#!/bin/bash

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
echo "Begin: fetch tak data files" >> ${logFile}
scp -o "PreferredAuthentications publickey" \
    -o "StrictHostKeyChecking no" \
    -o "UserKnownHostsFile /dev/null" \
    -o "IdentityFile ${sshIdentityFile}" \
    -r * ${scpTakDataUser}@${scpTakDataHost}:${scpTakDataRemotePath} ${coopImportFilesDir} >> ${logFile} 2>&1
echo "Done: fetch tak data files" >> ${logFile}

#=============================================================================
# Handle creation of new DB tables (to support rolling over old data, 1 backup)
# and import of data from TAK-exported files.
#=============================================================================
echo "Begin: create new tables: `date`" >> ${logFile}
groovy -Dgroovy.grape.report.downloads=true -Dgrape.root=./grape_repo CreateNewTables.groovy \
    -url ${coopJdbcUrl} -u ${coopJdbcUser} -p ${coopJdbcPassword} >> ${logFile} 2>&1
echo "Done: create new tables: `date`" >> ${logFile}

echo "Begin: import tak data from dir: ${coopImportFilesDir} : `date`" >> ${logFile}
groovy -Dgroovy.grape.report.downloads=true -Dgrape.root=./grape_repo TakCooperationImport.groovy \
    -url ${coopJdbcUrl} -u ${coopJdbcUser} -p ${coopJdbcPassword} -d ${coopImportFilesDir} >> ${logFile} 2>&1
echo "Done: import tak data: `date`" >> ${logFile}

echo "Begin: activate new tak data version: `date`" >> ${logFile}
groovy -Dgroovy.grape.report.downloads=true -Dgrape.root=./grape_repo ActivateNewVersion \
    -url ${coopJdbcUrl} -u ${coopJdbcUser} -p ${coopJdbcPassword} >> ${logFile} 2>&1
echo "Done: activate new tak data version: `date`" >> ${logFile}
