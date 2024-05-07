#=============================================================================
# Configure per environment.
#=============================================================================

#-----------------------------
# Cooperation config
#-----------------------------
coopJdbcUrl=${COOPERATION_DB_URL}
coopJdbcUser=${COOPERATION_DB_USER}
coopJdbcPassword=${COOPERATION_DB_PASSWORD}

coop_auth_user_and_pass=${COOPERATION_AUTH_USER_AND_PASS}

logFile=/dev/stdout
tmpDir=/tmp/cooperation-import-from-tak
coopImportFilesDir=${tmpDir}/import

#-----------------------------
# Cooperation config validering
#-----------------------------
#dump_files=(ntjp_test ntjp_qa ntjp_prod sll_qa sll_prod)
dump_files=(`echo $COOPERATION_IMPORT_ENVIRONMENTS | tr ',' ' '`)
connection_points_url=${COOPERATION_CONNECTION_POINTS_URL}

#-----------------------------
# SFTP settings for getting TAK data
#-----------------------------
sftpHost=${COOPERATION_SFTP_HOST}
sftpUser=${COOPERATION_SFTP_USER}
sftpRemotePath=${COOPERATION_SFTP_PATH}
sshIdentityFile=${COOPERATION_SFTP_KEYFILE}

#-----------------------------
# Script environment config
#-----------------------------
# set charset for java/groovy output
#   on Redhat EL 6: en_US.utf8
#   on MacOSX: en_US.UTF-8
export LC_CTYPE=en_US.utf8
# groovy must be in path
export PATH=${PATH}:/local/java/groovy/bin

#-----------------------------
# Mail for send fail report
#-----------------------------
smtp_login=${COOPERATION_MAIL_LOGIN}
smtp_host=${COOPERATION_MAIL_SERVER}
smtp_port=${COOPERATION_MAIL_PORT}
to_mail=${COOPERATION_MAIL_TO}
from_mail=${COOPERATION_MAIL_FROM}
alert_mail_subject=${COOPERATION_MAIL_SUBJECT}
alert_mail_text=${COOPERATION_MAIL_TEXT}
