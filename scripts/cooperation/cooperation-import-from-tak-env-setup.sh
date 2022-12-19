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

tmpDir=/tmp/cooperation-import-from-tak

coopImportFilesDir=${tmpDir}/import

#-----------------------------
# Cooperation config validering
#-----------------------------
dump_files=(ntjp_test ntjp_qa ntjp_prod sll_qa sll_prod)
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
to_mail=problem@inera.com,problem2@inera.com
from_mail=cooperationImportProblem@inera.se
alert_mail_subject="XXX miljo. Problem med Cooperation import"
alert_mail_text="Ett fel inträffades under cooperation import"
