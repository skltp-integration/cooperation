#!/usr/bin/env bash


## ------------------------------------------------------------------
## This script is used when importing database dumps
## on the amazon machine
## ------------------------------------------------------------------

# Exit on first error
set -o errexit

#JAVA_HOME=/usr/lib/jvm/java-6-sun
#export JAVA_HOME
#GROOVY_HOME=/usr/share/groovy
#export GROOVY_HOME
#PATH=$JAVA_HOME/bin:$GROOVY_HOME/bin:"${PATH}"
#export PATH


LOG_FILE="$0"_$(date +"%F").log

LOCK_FILE=/tmp/"$0".lock
if [ -f "$LOCK_FILE" ]; then
  echo "$(date +"%F %T") : script is already running" | tee -a $LOG_FILE
  exit
fi
trap "rm -f $LOCK_FILE" EXIT
touch $LOCK_FILE


DATABASE_FILE=~/cooperation.mv.db

echo "$(date +"%F %T") : Backing upp file $DATABASE_FILE" | tee -a $LOG_FILE
[ -f "$DATABASE_FILE" ] && zip "$DATABASE_FILE"_$(date +"%F-%H.%M.%S").zip "$DATABASE_FILE"

~/TakCooperationImport.groovy "--url" "jdbc:h2:tcp://localhost/~/cooperation" "-u" "sa" "-p" " " "-d" "~/dumpdata" "--clear" | tee -a $LOG_FILE

