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
latestDir="${COOPERATION_ARCHIVE_DIR}/latest"
rm -rf ${currentDir}

outfileBefore=${coopImportFilesDir}/before.txt
outfileAfter=${coopImportFilesDir}/after.txt
rm -rf ${outfileBefore}
rm -rf ${outfileAfter}

dump_files=(`echo $COOPERATION_IMPORT_ENVIRONMENTS | tr ',' ' '`)

printlog "INFO" "Begin: ny cooperation import"

#=============================================================================
# Verify before import
#=============================================================================
printlog "INFO" "Begin: Verify before"

exitCode=0
groovy VerifyCooperation.groovy \
    -d "${COOPERATION_IMPORT_ENVIRONMENTS}" \
    -url "${COOPERATION_CONNECTION_POINTS_URL}" \
    -out "${outfileBefore}" \
    -auth "${COOPERATION_AUTH_USER_AND_PASS}" || exitCode=$?

if [ "$COOPERATION_FORCE_IMPORT" == "true" ]; then
  printlog "INFO" "Force import active, will proceed even if up-to-date."
elif [ $exitCode -eq 0 ]; then
  printlog "INFO" "Cooperation is up-to-date, aborting."
  exit 0
fi

printlog "INFO" "Done: Verify before"

#=============================================================================
# Fetching TAK-data files from SFTP-server
#=============================================================================
printlog "INFO" "Begin: SFTP-download of TAK export files"
sftpOut=$(sftp -o IdentityFile=${COOPERATION_SFTP_KEYFILE} ${COOPERATION_SFTP_USER}@${COOPERATION_SFTP_HOST} 2>&1 <<EOF
get ${COOPERATION_SFTP_PATH}*.json ${coopImportFilesDir}
EOF)
printlog "INFO" "$sftpOut"

cp -r ${coopImportFilesDir} ${currentDir}

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
  fi
done

if (( ${#no_existing_dumps[@]} )); then
  printlog "ERROR" "Error: Not existing dumps: ${no_existing_dumps[@]}"
fi

printlog "INFO" "Done: Check file list: OK"

#=============================================================================
# Transform
#=============================================================================

printlog "INFO"  "Begin: transform tak export files in dir: ${coopImportFilesDir} : `date`"

transformed_dumps=()

for dump_file in "${dump_files[@]}"
do
  fileName="takdump_${dump_file}.json"
  groovy TransformTakExportFormatToCooperationImportFormat.groovy -d ${coopImportFilesDir} -f ${fileName} || true
  if [ $? -eq 0 ]; then
    transformed_dumps+=("$dump_file")
  else
    printlog "ERROR" "Failed to transform ${fileName}"
    if [ -f "${latestDir}/${fileName}" ]; then
      printlog "WARN" "Using older version of ${fileName}"
      cp ${latestDir}/${fileName} ${coopImportFilesDir}
      groovy TransformTakExportFormatToCooperationImportFormat.groovy -d ${coopImportFilesDir} -f ${fileName} || true
      if [ $? -eq 0 ]; then
        transformed_dumps+=("$dump_file")
      else
        printlog "ERROR" "Failed to transform older version of ${fileName}"
      fi
    fi
  fi
done

import_dumps=$(IFS=','; echo "${transformed_dumps[*]}")
printlog "INFO" "Successful transformations: ${import_dumps}"

printlog "INFO"  "Done: transform tak export files: `date`"

#=============================================================================
# Import and activate
#=============================================================================

printlog "INFO"  "Begin: import tak data from dir: ${coopImportFilesDir} : `date`"
groovy TakCooperationImport.groovy \
    -url ${COOPERATION_DB_URL} -u ${COOPERATION_DB_USER} -p ${COOPERATION_DB_PASSWORD} \
    -d ${coopImportFilesDir} -e ${import_dumps}
printlog "INFO"  "Done: import tak data: `date`"

printlog "INFO"  "Begin: activate new tak data version: `date`"
groovy ActivateNewVersion \
    -url ${COOPERATION_DB_URL} -u ${COOPERATION_DB_USER} -p ${COOPERATION_DB_PASSWORD}
printlog "INFO"  "Done: activate new tak data version: `date`"

#=============================================================================
# Verify after import
#=============================================================================
printlog "INFO" "Begin: Verify after"

exitCode=0
groovy VerifyCooperation.groovy \
    -d "${COOPERATION_IMPORT_ENVIRONMENTS}" \
    -url "${COOPERATION_CONNECTION_POINTS_URL}" \
    -out "${outfileAfter}" \
    -auth "${COOPERATION_AUTH_USER_AND_PASS}" || exitCode=$?

if [ $exitCode -eq 0 ]; then
  rm -rf ${latestDir}
  cp -r ${currentDir} ${latestDir}
fi

printlog "INFO" "Done: Verify after"
