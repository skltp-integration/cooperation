import groovy.sql.BatchingPreparedStatementWrapper
import groovy.sql.Sql
import org.slf4j.Logger

public class TableInstalledContract extends Table {

    private static final String SQL_CREATE_TABLE = multiline(
        "CREATE TABLE %TABLE% (",
        "   id bigint(20) NOT NULL AUTO_INCREMENT,",
        "   tmp_environment varchar(255) DEFAULT NULL,",
        "   tmp_platform varchar(255) DEFAULT NULL,",
        "   tmp_namespace varchar(255) DEFAULT NULL,",
        "   PRIMARY KEY (id)",
        ") ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET= utf8")

    private static final String SQL_INSERT = "insert into %TABLE% (tmp_environment, tmp_platform, tmp_namespace) values(?, ?, ?)"

    public TableInstalledContract(Sql db, Logger logger, String tableSuffix) {
        super(db, logger, "installedcontract", tableSuffix, SQL_CREATE_TABLE, SQL_INSERT)
    }

    public void addIndexes() {
        addKey("tmp_environment", "tmp_index_1")
        addKey("tmp_platform", "tmp_index_2")
        addKey("tmp_namespace", "tmp_index_3")
    }

    public void addForeignKeys() {
        addForeignKeyIdColumn("connection_point_id")
        addForeignKeyIdColumn("service_contract_id")
       
        addForeignKeyColumnData("connection_point_id", "connectionpoint", [["tmp_environment", "environment"], ["tmp_platform", "platform"]])
        addForeignKeyColumnData("service_contract_id", "servicecontract", [["tmp_namespace", "namespace"]])

        addForeignKeyConstraint("connection_point_id", "connectionpoint", "FK_installedcontract_1")
        addForeignKeyConstraint("service_contract_id", "servicecontract", "FK_installedcontract_2")

        addKey("connection_point_id", "IX_installedcontract_1")
        addKey("service_contract_id", "IX_installedcontract_2")
    }

    public void removeTempColumnsAndIndex() {
        dropTempColumn("environment")
        dropTempColumn("platform")
        dropTempColumn("namespace")
    }

    protected void addBatchEntries(Object input, String platform, String environment, BatchingPreparedStatementWrapper ps) {
        input.data.tjanstekontrakt.each {
            def namespace = it.namnrymd
            boolean isNew = checkNotDuplicate(namespace, environment, platform)
            if (isNew) {
                ps.addBatch([environment, platform, namespace])
            }
        }
    }
}
