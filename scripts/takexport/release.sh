#!/bin/bash

gitVersion=`git log --pretty=format:'%h' -n 1`
versionFile=release-version-$gitVersion.txt
archiveFile=takexport-to-cooperation-$gitVersion.tar


# create archive file
echo "Git commit: $gitVersion" > $versionFile

groovy -Dgrape.root=./grape_repo grabit.groovy

cd ..
tar -cv --exclude '\.*' -f $archiveFile takexport

mkdir takexport/build
mv $archiveFile takexport/build
rm takexport/$versionFile
echo "Done."
echo "Release archive: build/$archiveFile"
