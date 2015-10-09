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

if [ "$(ls -A incoming_data)" ]
then
  DATABASE_FILE=~/cooperation.mv.db
  BACKUP_DIR=./backup
  [ -d $BACKUP_DIR ] || mkdir -p $BACKUP_DIR | tee -a $LOG_FILE
  echo "$(date +"%F %T") : Backing upp file $DATABASE_FILE" | tee -a $LOG_FILE
  [ -f "$DATABASE_FILE" ] && zip $BACKUP_DIR/"$(basename $DATABASE_FILE)"_$(date +"%F-%H.%M.%S").zip "$DATABASE_FILE"

  echo "$(date +"%F %T") : Backing up dumpdata directory" | tee -a $LOG_FILE
  zip -r $BACKUP_DIR/dumpdata_$(date +"%F-%H.%M.%S").zip dumpdata/ | tee -a $LOG_FILE

  echo "$(date +"%F %T") : Backing upp incoming_data directory" | tee -a $LOG_FILE
  zip -r $BACKUP_DIR/incoming_data_$(date +"%F-%H.%M.%S").zip incoming_data/ | tee -a $LOG_FILE

  echo "$(date +"%F %T") : Copying incoming_data files to dumpdata directory" | tee -a $LOG_FILE
  cp -f incoming_data/* dumpdata
  rm -f incoming_data/*

  ~/TakCooperationImport.groovy "--url" "jdbc:h2:tcp://localhost/~/cooperation" "-u" "sa" "-p" "" "-d" "~/dumpdata" "--clear" | tee -a $LOG_FILE
else
  echo "$(date +"%F %T") : No incoming data. Nothing to import so exiting now." | tee -a $LOG_FILE
fi



