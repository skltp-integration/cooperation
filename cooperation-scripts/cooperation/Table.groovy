import groovy.sql.BatchingPreparedStatementWrapper
import groovy.sql.Sql
import org.slf4j.Logger

/**
 * Base class for representing database tables.
 * Implement a subclass for each table
 * 
 * Steps:
 * 1. createTable()
 *    The table will be created in the database (existing table will be dropped first if it exists)
 *    At this stage the table will not contain any indexes besides the primary key and the foreign
 *    key columns are missing. There are also some temporary columns that will later be used when
 *    creating foreign keys.
 * 2. addTableData()
 *    Entries read from imported tak-json files will be written to the database using bulk insert
 *    There might be duplicated entries (entries that share a given set of properties). To avoid
 *    the overhead of checking for that in the database there is a local hashset of the concerned
 *    columns for all inserted entries.
 *    This should be called for each file that should be important. (Order might matter because
 *    items can be considered duplicates.)
 * 3. addIndexes()
 *    This will add indexes for columns that is used when creating foreign key columns in other
 *    tables. Make sure to add indexes for all columns that is used for table join in the next
 *    step.
 * 4. addForeignKeys()
 *    This will add foreign key columns (and indexes) and fill them with id data from related
 *    tables.
 * 5. removeTempColumnsAndIndex()
 *    This will drop the temporary columns and indexes that was used to match foreign keys but that
 *    should not remain in the table.
 * The reason behind this is database performance. Checking duplicates and inserting foreign keys
 * while inserting each entry is slow.
 * Note that each step must be completed for all tables until the next step can be called.
 */
public abstract class Table {

    // Batch size for bulk insert. Can be tweaked but doesn't seem to have large impact as
    // long as a reasonable value is used
    private static final int BATCH_SIZE = 2000

    private final Sql db
    private final Logger logger

    private final String tableName
    private final String tableSuffix

    private final String sqlDropTable
    private final String sqlCreateTable
    private final String sqlInsert

    private HashSet<String[]> storedEntries

    private int insertedItems
    private int insertedItemsTotal
    private int duplicatedItems
    private int duplicatedItemsTotal

    /**
     * All subclasses must use this constructor
     * 
     * @param db Database connection
     * @param logger Logger
     * @param tableBaseName Table name without suffix (e.g. "serviceproducer")
     * @param tableSuffix Table suffix (e.g. "_new")
     * @param sqlCreateTable Sql used to create the table (Do not hardcode table name, use "%TABLE%" instead)
     * @param sqlInsert Sql used for parameterized insert to the table
     */
    public Table(Sql db, Logger logger, String tableBaseName, String tableSuffix, String sqlCreateTable, String sqlInsert) {
        this.db = db
        this.logger = logger
        this.tableName = tableBaseName + tableSuffix
        this.tableSuffix = tableSuffix
        this.sqlDropTable = "DROP TABLE IF EXISTS ${tableName}".toString()
        this.sqlCreateTable = sqlCreateTable.replace("%TABLE%", tableName)
        this.sqlInsert = sqlInsert.replace("%TABLE%", tableName)
    }

    /**
     * Creates the table in the database
     * (Columns and indexes will be altered later)
     */
    public void createTable() {
        insertedItemsTotal = 0
        duplicatedItemsTotal = 0
        storedEntries = new HashSet<>()
        logWithSql("Dropping table ${tableName}", sqlDropTable)
        db.execute(sqlDropTable)
        logWithSql("Creating table ${tableName}", sqlCreateTable)
        db.execute(sqlCreateTable)
    }

    /**
     * Insert table data in database
     *
     * @param input Parsed data from JsonSlurper
     * @param platform Platform, e.g. "NTJP"
     * @param environment Environment, e.g. "QA"
     */
    public void addTableData(Object input, String platform, String environment) {
        insertedItems = 0
        duplicatedItems = 0
        logWithSql("Inserting data into table ${tableName} for platform ${platform}, environment ${environment}", sqlInsert)
        db.withBatch(BATCH_SIZE, sqlInsert, { ps -> addBatchEntries(input, platform, environment, ps) })
        insertedItemsTotal += insertedItems
        duplicatedItemsTotal += duplicatedItems
        logger.info("${insertedItems} rows were added to ${tableName} for platform ${platform}, environment ${environment}, ${duplicatedItems} entries were discarded because they contain duplicated data (total ${insertedItemsTotal} / ${duplicatedItemsTotal})")
    }

    /**
     * Add additional indexes (after data has been inserted)
     * This is the default implementation that does nothing.
     * Override if any indexes should be added.
     */
    public void addIndexes() {
        logger.info("No secondary indexes to add for table ${tableName}. (Foreign keys might be added later.)")
    }

    /**
     * Create foreign key columns and fill with data
     * This is the default implementation that does nothing.
     * Override if any foreign key columns should be added.
     */
    public void addForeignKeys() {
        logger.info("No foreign keys to add for table ${tableName}")
    }

    /**
     * Remove temporary columns and indexes
     * This is the default implementation that does nothing.
     * Override if any temporary column or index should be removed.
     */
    public void removeTempColumnsAndIndex() {
        logger.info("No temporary column to remove for table ${tableName}")
    }

    /**
     * Called from addTableData
     * Each subclass must override this method
     * 
     * @param input Parsed data from JsonSlurper
     * @param platform Platform, e.g. "NTJP"
     * @param environment Environment, e.g. "QA"
     * @param ps call addBatch() on this for each row that should be stored in the database
     */
    protected abstract void addBatchEntries(Object input, String platform, String environment, BatchingPreparedStatementWrapper ps)

    /**
     * Subclasses should use these to check if an entry can be added or should be considered a duplicate
     * E.g. a serviceproducer consists of description + hsa_id + environment + platform. If two entries
     * have the same hsa_id + environment + platform the second will be considered a duplicate and is
     * not added even if the hsa_id is different
     * Submitted data is stored automatically for further duplicate checks
     *
     * @param values The values of the value for those fields that should be considered
     *               (For the example above hsa_id, environment and platform)
     * @return True if the submitted item values was not considered a duplicate
     */
    protected boolean checkNotDuplicate(String... values) {
        // Simply concat values as strings and store in local hashset
        // (Consumes some RAM but not more than we can afford. Calculating a unique hash might
        // have been better from that point of view but would probably add some processing time.)
        // Note that toLowerCase() must be used because string comparision in the database is not 
        // case sensitive.
        boolean isAdded = storedEntries.add(String.join("££", values).toLowerCase())
        if (isAdded) {
            insertedItems++
        } else {
            duplicatedItems++
        }
        return isAdded
    }

    /**
     * Used by subclasses to add key constraints to the table
     *
     * @param column Column to add the key for
     * @param keyName The constraint name
     */
    protected void addKey(String column, String keyName) {
        String sql = multiline(
            "ALTER TABLE ${tableName}",
            "ADD KEY ${keyName} (${column})")
        logWithSql("Adding key ${keyName} for ${tableName}:${column}", sql)
        db.execute(sql)
    }

    /**
     * Used by subclasses to add unique key constraints to the table
     *
     * @param column Column to add the key for
     * @param keyName The constraint name
     */
    protected void addUniqueKey(String column, String keyName) {
        String sql = multiline(
            "ALTER TABLE ${tableName}",
            "ADD UNIQUE KEY ${keyName} (${column})")
        logWithSql("Adding unique key ${keyName} for ${tableName}:${column}", sql)
        db.execute(sql)
    }

    /**
     * Used by subclasses to add foreign key column
     *
     * @param column Column name
     */
    protected void addForeignKeyIdColumn(String column) {
        String sql = multiline(
            "ALTER TABLE ${tableName}",
            "ADD COLUMN ${column} bigint(20) DEFAULT NULL")
        logWithSql("Adding foreign key column ${tableName}:${column}", sql)
        db.execute(sql)
    }

    /**
     * Used by subclasses to fill a foreign key column with values
     *
     * @param column Foreign key column
     * @param foreignTable Referenced table
     * @param columnPairs List of pairs of columns to joint table on.
     *   Each pair should consist of local column + foreign table column
     */
    protected void addForeignKeyColumnData(String column, String foreignTable, List<List<String>> columnPairs) {
        String otherTable = foreignTable + tableSuffix
        ArrayList<String> rows = new ArrayList<>()
        rows.add("UPDATE")
        rows.add("${tableName} INNER JOIN ${otherTable}")
        rows.add("	ON ${tableName}.${columnPairs[0][0]} = ${otherTable}.${columnPairs[0][1]}")
        for (int pairIndex = 1; pairIndex < columnPairs.size(); pairIndex++) {
            rows.add("	AND ${tableName}.${columnPairs[pairIndex][0]} = ${otherTable}.${columnPairs[pairIndex][1]}")
        }
        rows.add("SET ${tableName}.${column} = ${otherTable}.id")
        String sql = multiline(rows as String[])
        logWithSql("Adding data to foreign key column ${tableName}:${column}", sql)
        db.execute(sql)
    }

    /**
     * Used by subclasses to add foreign key constraints
     *
     * @param column Column to add the key for
     * @param foreignTable The table the foreign key is referencing
     * @param keyName The constraint name
     */
    protected void addForeignKeyConstraint(String column, String foreignTable, String keyName) {
        String otherTable = foreignTable + tableSuffix
        String sql = multiline(
            "ALTER TABLE ${tableName}",
            "ADD CONSTRAINT ${keyName}${tstamp()} FOREIGN KEY (${column}) REFERENCES ${otherTable}(id)")
        logWithSql("Adding foreign key ${keyName} for ${tableName}:${column} => ${otherTable}:id", sql)
        db.execute(sql)
    }

    /**
     * Used by subclasses to drop any temporary column
     *
     * @param column Column to drop
     */
    protected void dropTempColumn(String column) {
        String sql = multiline(
            "ALTER TABLE ${tableName}",
            "DROP COLUMN tmp_${column}")
        logWithSql("Dropping temporary column ${tableName}:${column}", sql)
        db.execute(sql)
    }

    /**
     * Used by subclasses to drop any temporary index
     *
     * @param index Index to drop
     */
    protected void dropIndex(String index) {
        String sql = multiline(
            "ALTER TABLE ${tableName}",
            "DROP INDEX ${index}")
        logWithSql("Dropping index ${tableName}:${index}", sql)
        db.execute(sql)
    }

    /**
     * Namespace to domain converter
     * (E.g. urn:riv:infrastructure:directory:organization:GetUnitResponder:1 => infrastructure:directory:organization)
     *
     * @param namespace Namespace
     * @return Domain
     */
    protected String domain(namespace) {
        String temp = namespace.replaceFirst("urn:riv:", "")
        String domain = temp.split(":[A-Z]")[0]
        return domain
    }

    /**
     * Concat strings with line breaks (for logging)
     *
     * @param lines Strings to concat
     * @return Concat string
     */
    protected static String multiline(String... lines) {
        return lines.join("\n").toString()
    }

    /**
     * Log message + sql
     *
     * @param msg Log message
     * @param sql Sql string for extended logging
     */
    private void logWithSql(String msg, String sql) {
        logger.info(msg)
        // Uncomment for easier debugging
        //logger.debug(sql)
    }

    /**
     * This weird time stamp is used as part of name for some table constraints
     * (Don't know why but lets keep it like that for now)
     *
     * @return time stamp
     */
    private String tstamp() {
	    return new Date().format("ddHHmm")
    }

}
