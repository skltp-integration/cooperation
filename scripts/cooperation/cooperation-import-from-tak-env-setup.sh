#=============================================================================
# Configure per environment.
#=============================================================================

#-----------------------------
# Cooperation config
#-----------------------------
coopJdbcUrl=jdbc:mysql://localhost/cooperation
coopJdbcUser=coop_user
coopJdbcPassword=coop_pw

tmpDir=/tmp/cooperation-import-from-tak

coopImportFilesDir=${tmpDir}/import

#-----------------------------
# Cooperation config validering
#-----------------------------
dump_files=(ntjp_test ntjp_qa ntjp_prod sll_qa sll_prod)
connection_points_url=http://<cooperation_url>/coop/api/v1/connectionPoints

#-----------------------------
# SFTP settings for getting TAK data
#-----------------------------
sftpHost=ine-pib-misc01.sth.basefarm.net
sftpUser=coop-user
sftpRemotePath="/upload/"
sshIdentityFile=~/.ssh/id_rsa_tak_data_transfer

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
