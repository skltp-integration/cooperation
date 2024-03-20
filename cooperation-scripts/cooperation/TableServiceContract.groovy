import groovy.sql.BatchingPreparedStatementWrapper
import groovy.sql.Sql
import org.slf4j.Logger

public class TableServiceContract extends Table {

    private static final String SQL_CREATE_TABLE = multiline(
        "CREATE TABLE %TABLE% (",
        "   id bigint(20) NOT NULL AUTO_INCREMENT,",
        "   major int(11) DEFAULT NULL,",
        "   minor int(11) DEFAULT NULL,",
        "   name varchar(255) DEFAULT NULL,",
        "   namespace varchar(255) DEFAULT NULL,",
        "   tmp_namespace varchar(255) DEFAULT NULL,",
        "   PRIMARY KEY (id)",
        ") ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET= utf8")

    private static final String SQL_INSERT = "insert into %TABLE% (major, minor, name, namespace, tmp_namespace) values(?, ?, ?, ?, ?)"

    public TableServiceContract(Sql db, Logger logger, String tableSuffix) {
        super(db, logger, "servicecontract", tableSuffix, SQL_CREATE_TABLE, SQL_INSERT)
    }

    public void addIndexes() {
        addUniqueKey("namespace", "UK_servicecontract_1")
        addKey("tmp_namespace", "tmp_index_1")
    }

    public void addForeignKeys() {
        addForeignKeyIdColumn("service_domain_id")
        addForeignKeyColumnData("service_domain_id", "servicedomain", [["tmp_namespace", "namespace"]])
        addForeignKeyConstraint("service_domain_id", "servicedomain", "FK_servicecontract_1")
    }

    public void removeTempColumnsAndIndex() {
        dropTempColumn("namespace")
    }

    protected void addBatchEntries(Object input, String platform, String environment, BatchingPreparedStatementWrapper ps) {
        input.data.tjanstekontrakt.each {
            def major = it.majorVersion
            def minor = it.minorVersion
            def name = it.beskrivning
            def namespace = it.namnrymd
            def tmp_namespace = domain(namespace)
            boolean isNew = checkNotDuplicate(namespace)
            if (isNew) {
                ps.addBatch([major, minor, name, namespace, tmp_namespace])
            }
        }
    }
}
