#!/bin/bash

#=============================================================================
# Init.
#=============================================================================

tmpDir=/tmp/verify-cooperation
outfile=${tmpDir}/out.txt
mkdir -p ${tmpDir}
rm -rf ${outfile}

#=============================================================================
# Verify and notify on error.
#=============================================================================

groovy VerifyCooperation.groovy \
    -d "${COOPERATION_IMPORT_ENVIRONMENTS}" \
    -url "${COOPERATION_CONNECTION_POINTS_URL}" \
    -out "${outfile}" \
    -auth "${COOPERATION_AUTH_USER_AND_PASS}"

error=$?

sleep 10

if [ ! -f ${outfile} ]; then
    echo "Cooperation import verification lyckades inte." > ${outfile}
    error=1
fi

if [ ! $error -eq 0 ]; then
  groovy SendAlertEmail.groovy \
      -login ${COOPERATION_MAIL_LOGIN} \
      -host ${COOPERATION_MAIL_SERVER} \
      -port ${COOPERATION_MAIL_PORT} \
      -to ${COOPERATION_MAIL_TO} \
      -from ${COOPERATION_MAIL_FROM} \
      -subj "${COOPERATION_MAIL_SUBJECT}" \
      -file "${outfile}"
fi
