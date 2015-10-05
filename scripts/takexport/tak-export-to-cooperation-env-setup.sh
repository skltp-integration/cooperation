#=============================================================================
# Configure per environment.
#=============================================================================

#-----------------------------
# TAK config
#-----------------------------
# takSite: [ntjp | sll]
takSite=ntjp
# takEnvironment: [prod | qa]
takEnvironment=qa
takDbUser=ntjpstat
takDbPassword=XXX
takDbName=takv2
takDbHost=ine-sib-mysql02.sth.basefarm.net

#-----------------------------
# Script environment config
#-----------------------------
sshIdentityFile=~/.ssh/id_rsa_tak_data_transfer
tmpDir=/tmp/tak-export-to-cooperation
# set charset for java/groovy output
#   on Redhat EL 6: en_US.utf8
#   on MacOSX: en_US.UTF-8
export LC_CTYPE=en_US.utf8
# groovy must be in path
export PATH=${PATH}:/local/java/groovy/groovy-2.3.8/bin

#-----------------------------
# Cooperation config
#-----------------------------
cooperationHost=ec2-52-19-89-21.eu-west-1.compute.amazonaws.com
cooperationUser=ec2-user
cooperationRemotePath="~/incoming_data"
