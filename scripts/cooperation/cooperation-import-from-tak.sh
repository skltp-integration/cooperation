#!/bin/bash

printlog(){
	level=$1
	message=$2
	while IFS=$'\n' read -r currentMessage; do
 	printf "{\"@timestamp\":\"$(date '+%Y-%m-%dT%T.%3N')\",\"level\":\"%s\",\"message\":\"%s\"}\n" "$level" "$currentMessage"
	done <<< "$message"
}


if [[ $EUID -eq 0 ]]; then
   echo "This script must be run as ind-app"
   exit 1
fi

#=============================================================================
# Simple check to avoid running multiple instances via cron
#=============================================================================
lock=/tmp/cooperation-import-from-tak-lockdir

if mkdir ${lock}; then
  trap 'rmdir ${lock}' EXIT
else
  exit 1
fi

#=============================================================================
#=============================================================================
# break on error
set -e

cd `dirname $0`
#=============================================================================
# Configure per environment.
#=============================================================================
# setup environment
. cooperation-import-from-tak-env-setup.sh

#=============================================================================
# Init.
#=============================================================================
mkdir -p ${tmpDir}
mkdir -p ${tmpDir}/import
rm ${logFile}
# clear logfile
printlog "INFO" "Begin: ny cooperation import" >> ${logFile}
printlog "INFO" "Begin: environment check" >> ${logFile}
# check that groovy is available
groovyVesion=$(groovy -version)
printlog "INFO" "$groovyVesion" >> ${logFile} 2>&1
printlog "INFO" "Done: environment check" >> ${logFile}

#=============================================================================
# Fetching TAK-data files from SFTP-server
#=============================================================================
printlog "INFO" "Begin: SFTP-download of TAK export files" >> ${logFile}
sftpOut=$(sftp -o IdentityFile=${sshIdentityFile} ${sftpUser}@${sftpHost} 2>&1 <<EOF
get ${sftpRemotePath}*.json ${coopImportFilesDir}
EOF)
printlog "INFO" "$sftpOut" >> ${logFile}
printlog "INFO" "Done: SFTP-download of TAK export files" >> ${logFile}

#=============================================================================
# Check dump-file list
#=============================================================================
printlog "INFO" "Begin: Check file list" >> ${logFile}

no_existing_dumps=()

for dump_file in "${dump_files[@]}"
do
file_exists=false
for file_ in "${coopImportFilesDir}"/*
do
if $file_exists
then
continue
fi

if [[ "$file_" == *"$dump_file"* ]]; then
file_exists=true
fi
done

if [ "$file_exists" = false ]
then
no_existing_dumps+=("$dump_file")
fi

done

if (( ${#no_existing_dumps[@]} )); then
printlog "ERROR" "Error: Not existing dumps: ${no_existing_dumps[@]}" >> ${logFile}
echo "Error: No existing dumps: ${no_existing_dumps[@]}"
mail -s "$alert_mail_subject" $to_mail <<< "Not existing dumps: ${no_existing_dumps[@]}"
fi
printlog "INFO" "Done: Check file list: OK" >> ${logFile}


#=============================================================================
# Handle creation of new DB tables (to support rolling over old data, 1 backup)
# and import of data from TAK-exported files.
# (No longer used, temp. tables are created by TakCooperationImport.groovy)
#=============================================================================
#printlog "INFO"  "Begin: create new tables: `date`" >> ${logFile}
#groovy CreateNewTables.groovy \
#    -url ${coopJdbcUrl} -u ${coopJdbcUser} -p ${coopJdbcPassword} -s _new
#printlog "INFO"  "Done: create new tables: `date`" >> ${logFile}

printlog "INFO"  "Begin: transform tak export files in dir: ${coopImportFilesDir} : `date`" >> ${logFile}
groovy TransformTakExportFormatToCooperationImportFormat.groovy \
    -d ${coopImportFilesDir}
printlog "INFO"  "Done: transform tak export files: `date`" >> ${logFile}

printlog "INFO"  "Begin: import tak data from dir: ${coopImportFilesDir} : `date`" >> ${logFile}
groovy TakCooperationImport.groovy \
    -url ${coopJdbcUrl} -u ${coopJdbcUser} -p ${coopJdbcPassword} -d ${coopImportFilesDir}
printlog "INFO"  "Done: import tak data: `date`" >> ${logFile}

printlog "INFO"  "Begin: activate new tak data version: `date`" >> ${logFile}
groovy ActivateNewVersion \
    -url ${coopJdbcUrl} -u ${coopJdbcUser} -p ${coopJdbcPassword}
printlog "INFO"  "Done: activate new tak data version: `date`" >> ${logFile}
