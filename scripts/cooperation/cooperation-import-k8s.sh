#!/bin/bash

#=============================================================================
# Logging helper method
#=============================================================================

printlog(){
	level=$1
	message=$2
	while IFS=$'\n' read -r currentMessage; do
 	printf "{\"@timestamp\":\"$(date --utc '+%Y-%m-%dT%T.%3NZ')\",\"log.level\":\"%s\",\"message\":\"%s\"}\n" "$level" "$currentMessage"
	done <<< "$message"
}

#=============================================================================
# Init.
#=============================================================================

# break on error
set -e

# create directories
tmpDir=/tmp/cooperation-import-from-tak
coopImportFilesDir=${tmpDir}/import
mkdir -p ${tmpDir}
mkdir -p ${tmpDir}/import

currentDir="${COOPERATION_ARCHIVE_DIR}/current"
successDir="${COOPERATION_ARCHIVE_DIR}/success"
failDir="${COOPERATION_ARCHIVE_DIR}/fail"
rm -rf ${currentDir}

dump_files=(`echo $COOPERATION_IMPORT_ENVIRONMENTS | tr ',' ' '`)

printlog "INFO" "Begin: ny cooperation import"

#=============================================================================
# Fetching TAK-data files from SFTP-server
#=============================================================================
printlog "INFO" "Begin: SFTP-download of TAK export files"
sftpOut=$(sftp -o IdentityFile=${COOPERATION_SFTP_KEYFILE} ${COOPERATION_SFTP_USER}@${COOPERATION_SFTP_HOST} 2>&1 <<EOF
get ${COOPERATION_SFTP_PATH}*.json ${coopImportFilesDir}
EOF)
printlog "INFO" "$sftpOut"
printlog "INFO" "Done: SFTP-download of TAK export files"

#=============================================================================
# Check dump-file list
#=============================================================================
printlog "INFO" "Begin: Check file list"

no_existing_dumps=()

for dump_file in "${dump_files[@]}"
do
  path="${coopImportFilesDir}/takdump_${dump_file}.json"
  if [ ! -f $path ]; then
    no_existing_dumps+=("$dump_file")
  else
    md5sum $path >> checksums.md5
  fi
done

if (( ${#no_existing_dumps[@]} )); then
printlog "ERROR" "Error: Not existing dumps: ${no_existing_dumps[@]}"
mail -s "${COOPERATION_MAIL_SUBJECT}" ${COOPERATION_MAIL_TO} <<< "Not existing dumps: ${no_existing_dumps[@]}"
fi

printlog "INFO" "Done: Check file list: OK"

#=============================================================================
# Check for changes
#=============================================================================
printlog "INFO" "Begin: Checking for changes"

changes=$(diff ${coopImportFilesDir}/checksums.md5 ${successDir}/checksums.md5 2>&1 || true)
if [ "$changes" == "" ]; then
    printlog "INFO" "No change since last successful import, aborting."
    exit 0
fi

cp -r ${coopImportFilesDir} ${currentDir}

printlog "INFO" "Done: Checking for changes: Changes detected, proceeding."

#=============================================================================
# Transform
#=============================================================================

printlog "INFO"  "Begin: transform tak export files in dir: ${coopImportFilesDir} : `date`"
groovy TransformTakExportFormatToCooperationImportFormat.groovy \
    -d ${coopImportFilesDir}
printlog "INFO"  "Done: transform tak export files: `date`"

#=============================================================================
# Import and activate
#=============================================================================

printlog "INFO"  "Begin: import tak data from dir: ${coopImportFilesDir} : `date`"
groovy TakCooperationImport.groovy \
    -url ${COOPERATION_DB_URL} -u ${COOPERATION_DB_USER} -p ${COOPERATION_DB_PASSWORD} -d ${coopImportFilesDir}
printlog "INFO"  "Done: import tak data: `date`"

printlog "INFO"  "Begin: activate new tak data version: `date`"
groovy ActivateNewVersion \
    -url ${COOPERATION_DB_URL} -u ${COOPERATION_DB_USER} -p ${COOPERATION_DB_PASSWORD}
printlog "INFO"  "Done: activate new tak data version: `date`"

cp -r ${currentDir} ${successDir}
