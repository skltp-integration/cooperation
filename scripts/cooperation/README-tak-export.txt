============================================================
README.txt
============================================================
Scripts to export data from TAK-database and upload to a host running the
cooperation-application.

1. TakExport.groovy
    Export data from TAK-database.

2. grabit.groovy
    Grab groovy dependencies to a local repo for the TakExport.groovy script.

3. tak-export-to-cooperation.sh
    Intended for automation using cron. Runs TAK export and uploads the export
    file to a host using scp.

4. tak-export-to-cooperation-env-setup.sh
    Configuration for all environment variables for the
    tak-export-to-cooperation.sh script.

5. release.sh
    Build a release-bundle for deployment.
