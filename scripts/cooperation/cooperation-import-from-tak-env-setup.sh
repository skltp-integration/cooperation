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
# SCP settings for getting TAK data
#-----------------------------
sshIdentityFile=~/.ssh/id_rsa_tak_data_transfer
scpTakDataHost=localhost
scpTakDataUser=coop-user
scpTakDataRemotePath="~/tak_data"

#-----------------------------
# Script environment config
#-----------------------------
# set charset for java/groovy output
#   on Redhat EL 6: en_US.utf8
#   on MacOSX: en_US.UTF-8
export LC_CTYPE=en_US.utf8
# groovy must be in path
export PATH=${PATH}:/local/java/groovy/bin
