Test of transform-script for the TAK-export format to the format used by
cooperation:

TransformTakExportFormatToCooperationImportFormat.groovy


Example files
------------------------------
1. takdump_ntjp_test.json-from-old-cooperation-tak-export-script.json
Export from TAK done using the old export script from cooperation:
  TakCooperationExport.groovy

2. takdump_ntjp_test.json-from-tak-export.json.original.before.transform
Export from TAK done using the new (official) export script owned by the
TAK-component.
See: https://github.com/skltp/tak/tree/develop/tak-integration/export

3. takdump_ntjp_test.json-from-tak-export.json
The transformed TAK-export, now in cooperation format.


Test and verification
------------------------------
The script:
  ExtractStatisticsFromTakExportFile.groovy
can be used to output statistics/counts for the TAK-export files like:

groovy ExtractStatisticsFromTakExportFile.groovy <TAK-export-file> > <stats-file>

Run the above script and do a file-diff to find and suspects.
Secondly, for every entity type (like "vagval"), do a manual inspection
of at least three records.
