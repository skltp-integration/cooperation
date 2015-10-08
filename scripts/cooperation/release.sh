#!/bin/bash

#===========================================================
# Build a release package
#===========================================================
baseDir=${PWD##*/}
gitVersion=`git log --pretty=format:'%h' -n 1`
versionFile=release-version-$gitVersion.txt
archiveFile=skltp-cooperation-export-import-$gitVersion.tar

# create version info file
echo "Git commit: $gitVersion" > $versionFile
cmd="git status"
echo ${cmd} >> $versionFile
eval ${cmd} >> $versionFile

# pre-run groovy grab for packaging for environments without internet connection
# NOTE: 2015-10-08: problems getting hsqldb using grape, had to populate local maven repo first using:
# mvn org.apache.maven.plugins:maven-dependency-plugin:get -DartifactId=hsqldb -DgroupId=org.hsqldb -Dversion=2.3.3
groovy -Dgrape.root=./grape_repo grabit.groovy

# create archive
cd ..
tar -cv --exclude '\.*' --exclude 'takdump*' -f ${archiveFile} ${baseDir}

# cleanup
cd ${baseDir}
mkdir build
mv ../${archiveFile} build
rm ${versionFile}
echo "Done."
echo "Release archive: build/${archiveFile}"
