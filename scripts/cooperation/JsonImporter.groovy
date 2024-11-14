import groovy.json.JsonSlurper
import groovy.sql.Sql
import org.slf4j.Logger

public class JsonImporter {

    private static final String SEPARATOR_LINE = "============================================================"

    private final Sql db
    private final Logger logger
    private final List<String> platformEnvironments
    private final sourceDir
    private final List<Table> tables

    public JsonImporter(Sql db, Logger logger, List<String> platformEnvironments, String sourceDir, String tableSuffix) {
        this.db = db
        this.logger = logger
        this.platformEnvironments = platformEnvironments
        this.sourceDir = sourceDir
        tables = new ArrayList<>()
        addTables(db, tableSuffix)
    }

    public void importFromFiles() {
        logWithSeparator("Import from files to database - starting")
        db.execute "SET foreign_key_checks = 0"
        createTables()
        db.execute "SET foreign_key_checks = 1"
        addTableData()
        addIndexes()
        addForeignKeys()
        removeTempColumns()
        logWithSeparator("Import from files to database - done")
    }

    private void logWithSeparator(String msg) {
        logger.info(SEPARATOR_LINE)
        logger.info(msg)
        logger.info(SEPARATOR_LINE)
    }

    private void createTables() {
        logWithSeparator("Creating tables")
        for (Table table : tables) {
            table.createTable()
        }
    }

    private void addTableData() {
        logWithSeparator("Inserting table data")
        for (String platformEnvironment in platformEnvironments) {
            addTableData(platformEnvironment)
        }
    }

    private void addIndexes() {
        logWithSeparator("Indexing")
        for (Table table : tables) {
            table.addIndexes()
        }
    }

    private void addForeignKeys() {
        logWithSeparator("Adding foreign keys")
        for (Table table : tables) {
            table.addForeignKeys()
        }
    }

    private void removeTempColumns() {
        logWithSeparator("Removing temporary columns")
        for (Table table : tables) {
            table.removeTempColumnsAndIndex()
        }
    }

    private void addTables(db, String tableSuffix) {
        tables.add(new TableConnectionPoint(db, logger, tableSuffix))
        tables.add(new TableLogicalAddress(db, logger, tableSuffix))
        tables.add(new TableServiceConsumer(db, logger, tableSuffix))
        tables.add(new TableServiceDomain(db, logger, tableSuffix))
        tables.add(new TableServiceContract(db, logger, tableSuffix))
        tables.add(new TableServiceProducer(db, logger, tableSuffix))
        tables.add(new TableServiceProduction(db, logger, tableSuffix))
        tables.add(new TableCooperation(db, logger, tableSuffix))
        tables.add(new TableInstalledContract(db, logger, tableSuffix))
    }

    private void addTableData(String platformEnvironment)
    {
        String filename = platformEnvironmentToFileName(platformEnvironment)
        File sourceFile = new File(sourceDir, filename)
        if (!sourceFile.exists()) {
            logger.info("Source $filename not found - skipping")
            return
        }
        logWithSeparator("Inserting table data from $filename")
        Object input = null
        try {
            JsonSlurper slurper = new JsonSlurper().setType(JsonParserType.CHARACTER_SOURCE)
            input = slurper.parse(sourceFile)
        } catch (Exception exception) {
		    logger.error("Failed to read $filename", exception)
            throw exception
	    }
        String platform = platformEnvironmentToPlatform(platformEnvironment)
        String environment = platformEnvironmentToEnvironment(platformEnvironment)
        addTableData(input, platform, environment)
    }

    private addTableData(Object input, String platform, String environment) {
        for (Table table : tables) {
            table.addTableData(input, platform, environment)
        }
    }

    private static String platformEnvironmentToFileName(String platformEnvironment) {
        return "takdump_${platformEnvironment}.json"
    }

    private static String platformEnvironmentToPlatform(String platformEnvironment) {
        return platformEnvironment.split("_")[0].toUpperCase()
    }

    private static String platformEnvironmentToEnvironment(String platformEnvironment) {
        return platformEnvironment.split("_")[1].toUpperCase()
    }
}
