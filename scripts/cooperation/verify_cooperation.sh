#!/bin/bash

if [[ $EUID -eq 0 ]]; then
   echo "This script must be run as ind-app"
   exit 1
fi

cd `dirname $0`


printlog(){
	level=$1
	message=$2
	printf "{\"@timestamp\":\"$(date --utc '+%Y-%m-%dT%T.%3NZ')\",\"level\":\"%s\",\"message\":\"%s\"}\n" "$level" "$message"
}

#=============================================================================
# Configure per environment.
#=============================================================================
# setup environment
. cooperation-import-from-tak-env-setup.sh

mkdir -p ${tmpDir}
okFile=${tmpDir}/okfile
rm -f ${okFile}


dump=$( IFS=$'\n'; echo "${dump_files[*]}" )

groovy verify_cooperation.groovy \
    -smtp_prop ${smtp_props_file} \
    -mail ${to_mail} \
    -from_mail ${from_mail} \
    -subj "${alert_mail_subject}" \
    -dumps "${dump}" \
    -coop "${connection_points_url}" \
    -ok_file "${okFile}" \
    -user_and_pass "${coop_auth_user_and_pass}"

sleep 10

if [ ! -f ${okFile} ]; then
    mail -s "$alert_mail_subject" $to_mail <<< "Cooperation import verification lyckades inte."
fi
