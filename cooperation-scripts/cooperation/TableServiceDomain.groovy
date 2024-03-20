import groovy.sql.BatchingPreparedStatementWrapper
import groovy.sql.Sql
import org.slf4j.Logger

public class TableServiceDomain extends Table {

    private static final String SQL_CREATE_TABLE = multiline(
        "CREATE TABLE %TABLE% (",
        "   id bigint(20) NOT NULL AUTO_INCREMENT,",
        "   name varchar(255) DEFAULT NULL,",
        "   namespace varchar(255) DEFAULT NULL,",
        "   PRIMARY KEY (id)",
        ") ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET= utf8")

    private static final String SQL_INSERT = "insert into %TABLE% (name, namespace) values(?, ?)"

    public TableServiceDomain(Sql db, Logger logger, String tableSuffix) {
        super(db, logger, "servicedomain", tableSuffix, SQL_CREATE_TABLE, SQL_INSERT)
    }

    public void addIndexes() {
        addUniqueKey("namespace", "UK_servicedomain_1")        
    }

    protected void addBatchEntries(Object input, String platform, String environment, BatchingPreparedStatementWrapper ps) {
        input.data.tjanstekontrakt.each {
            def name = "namn" // Why use a constant string for all rows?
            def namespace = domain(it.namnrymd)
            boolean isNew = checkNotDuplicate(namespace)
            if (isNew) {
                ps.addBatch([name, namespace])
            }
        }
    }
}
