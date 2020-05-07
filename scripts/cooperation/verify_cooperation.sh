#!/bin/bash

if [[ $EUID -eq 0 ]]; then
   echo "This script must be run as ine-app" 
   exit 1
fi


#=============================================================================
# Configure per environment.
#=============================================================================
# setup environment
. cooperation-import-from-tak-env-setup.sh


okFile=${tmpDir}/okfile
rm -f ${okFile}


logFile=${tmpDir}/cooperation-import-verify.log
# clear logfile
date > ${logFile}



echo "Verify cooperation import" >> ${logFile}
dump=$( IFS=$'\n'; echo "${dump_files[*]}" )

groovy verify_cooperation.groovy \
    -smtp_prop "smtp.properties" -mail ${to_mail} -from_mail ${from_mail} -subj "${alert_mail_subject}" -dumps "${dump}" -coop "${connection_points_url}" -ok_file "${okFile}" >> ${logFile} 2>&1
	
sleep 10

if [ ! -f ${okFile} ]; then
    mail -s "$alert_mail_subject" $to_mail <<< "Cooperation import verification lyckades inte."
fi
	
echo "Done: Verify cooperation import" >> ${logFile}
