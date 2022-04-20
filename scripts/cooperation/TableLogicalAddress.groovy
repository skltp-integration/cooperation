import groovy.sql.BatchingPreparedStatementWrapper
import groovy.sql.Sql
import org.slf4j.Logger

public class TableLogicalAddress extends Table {

    private static final String SQL_CREATE_TABLE = multiline(
        "CREATE TABLE %TABLE% (",
        "   id bigint(20) NOT NULL AUTO_INCREMENT,",
        "   description varchar(255) DEFAULT NULL,",
        "   logical_address varchar(255) DEFAULT NULL,",
        "   PRIMARY KEY (id)",
        ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET= utf8")

    private static final String SQL_INSERT = "insert into %TABLE% (logical_address, description) values(?, ?)"

    public TableLogicalAddress(Sql db, Logger logger, String tableSuffix) {
        super(db, logger, "logicaladdress", tableSuffix, SQL_CREATE_TABLE, SQL_INSERT)
    }

    public void addIndexes() {
        addUniqueKey("logical_address", "UK_logicaladdress_1")
    }

    protected void addBatchEntries(Object input, String platform, String environment, BatchingPreparedStatementWrapper ps) {
        input.data.logiskadress.each {
            def logicalAddress = it.hsaId
            def description = it.beskrivning
            boolean isNew = checkNotDuplicate(logicalAddress)
            if (isNew) {
                ps.addBatch([logicalAddress, description])
            }
        }
    }
}
