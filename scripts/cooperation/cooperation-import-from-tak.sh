#!/bin/bash

if [[ $EUID -eq 0 ]]; then
   echo "This script must be run as ine-app" 
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
rm -fr ${tmpDir}
mkdir -p ${tmpDir}
mkdir -p ${tmpDir}/import
logFile=${tmpDir}/cooperation-import-fom-tak.log
# clear logfile
date > ${logFile}
echo "Begin: environment check" >> ${logFile}
# check that groovy is available
groovy -version >> ${logFile} 2>&1
# check locale
locale >> ${logFile} 2>&1
echo "Done: environment check" >> ${logFile}

#=============================================================================
# Fetching TAK-data files from SFTP-server
#=============================================================================
echo "Begin: SFTP-download of TAK export files" >> ${logFile}
sftp -o IdentityFile=${sshIdentityFile} ${sftpUser}@${sftpHost} >> ${logFile} 2>&1 <<EOF
get ${sftpRemotePath}*.json ${coopImportFilesDir}
EOF
echo "Done: SFTP-download of TAK export files" >> ${logFile}

#=============================================================================
# Check dump-file list
#=============================================================================
echo "Begin: Check file list" >> ${logFile}

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
	echo "Error: Not existing dumps: ${no_existing_dumps[@]}" >> ${logFile}
	echo "Error: No existing dumps: ${no_existing_dumps[@]}" 
	mail -s "$alert_mail_subject" $to_mail <<< "Not existing dumps: ${no_existing_dumps[@]}"
	exit -1
fi 
echo "Done: Check file list: OK" >> ${logFile}



#=============================================================================
# Handle creation of new DB tables (to support rolling over old data, 1 backup)
# and import of data from TAK-exported files.
#=============================================================================
echo "Begin: create new tables: `date`" >> ${logFile}
groovy CreateNewTables.groovy \
    -url ${coopJdbcUrl} -u ${coopJdbcUser} -p ${coopJdbcPassword} -s _new >> ${logFile} 2>&1
echo "Done: create new tables: `date`" >> ${logFile}

echo "Begin: transform tak export files in dir: ${coopImportFilesDir} : `date`" >> ${logFile}
groovy TransformTakExportFormatToCooperationImportFormat.groovy \
    -d ${coopImportFilesDir} >> ${logFile} 2>&1
echo "Done: transform tak export files: `date`" >> ${logFile}

echo "Begin: import tak data from dir: ${coopImportFilesDir} : `date`" >> ${logFile}
groovy TakCooperationImport.groovy \
    -url ${coopJdbcUrl} -u ${coopJdbcUser} -p ${coopJdbcPassword} -d ${coopImportFilesDir} >> ${logFile} 2>&1
echo "Done: import tak data: `date`" >> ${logFile}

echo "Begin: activate new tak data version: `date`" >> ${logFile}
groovy ActivateNewVersion \
    -url ${coopJdbcUrl} -u ${coopJdbcUser} -p ${coopJdbcPassword} >> ${logFile} 2>&1
echo "Done: activate new tak data version: `date`" >> ${logFile}
