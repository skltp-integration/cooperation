import groovy.sql.BatchingPreparedStatementWrapper
import groovy.sql.Sql
import org.slf4j.Logger

public class TableCooperation extends Table {

    private static final String SQL_CREATE_TABLE = multiline(
        "CREATE TABLE %TABLE% (",
        "   id bigint(20) NOT NULL AUTO_INCREMENT,",
        "   tmp_environment varchar(255) DEFAULT NULL,",
        "   tmp_platform varchar(255) DEFAULT NULL,",
        "   tmp_logical_address varchar(255) DEFAULT NULL,",
        "   tmp_namespace varchar(255) DEFAULT NULL,",
        "   tmp_hsa_id varchar(255) DEFAULT NULL,",
        "   PRIMARY KEY (id)",
        ") ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET= utf8")

    private static final String SQL_INSERT = "insert into %TABLE% (tmp_environment, tmp_platform, tmp_logical_address, tmp_namespace, tmp_hsa_id) values(?, ?, ?, ?, ?)"

    public TableCooperation(Sql db, Logger logger, String tableSuffix) {
        super(db, logger, "cooperation", tableSuffix, SQL_CREATE_TABLE, SQL_INSERT)
    }

    public void addIndexes() {
        addKey("tmp_environment", "tmp_index_1")
        addKey("tmp_platform", "tmp_index_2")
        addKey("tmp_logical_address", "tmp_index_3")
        addKey("tmp_namespace", "tmp_index_4")
        addKey("tmp_hsa_id", "tmp_index_5")
    }

    public void addForeignKeys() {
        addForeignKeyIdColumn("connection_point_id")
        addForeignKeyIdColumn("logical_address_id")
        addForeignKeyIdColumn("service_consumer_id")
        addForeignKeyIdColumn("service_contract_id")
  
        addForeignKeyColumnData("connection_point_id", "connectionpoint", [["tmp_environment", "environment"], ["tmp_platform", "platform"]])
        addForeignKeyColumnData("logical_address_id", "logicaladdress", [["tmp_logical_address", "logical_address"]])
        addForeignKeyColumnData("service_consumer_id", "serviceconsumer", [["tmp_environment", "tmp_environment"], ["tmp_platform", "tmp_platform"], ["tmp_hsa_id", "hsa_id"]])
        addForeignKeyColumnData("service_contract_id", "servicecontract", [["tmp_namespace", "namespace"]])

        addForeignKeyConstraint("connection_point_id", "connectionpoint", "FK_cooperation_2")
        addForeignKeyConstraint("logical_address_id", "logicaladdress", "FK_cooperation_3")
        addForeignKeyConstraint("service_consumer_id", "serviceconsumer", "FK_cooperation_1")
        addForeignKeyConstraint("service_contract_id", "servicecontract", "FK_cooperation_4")

        addKey("connection_point_id", "IX_cooperation_1")
        addKey("logical_address_id", "IX_cooperation_2")
        addKey("service_consumer_id", "IX_cooperation_3")
        addKey("service_contract_id", "IX_cooperation_4")
    }

    public void removeTempColumnsAndIndex() {
        dropTempColumn("environment")
        dropTempColumn("platform")
        dropTempColumn("logical_address")
        dropTempColumn("namespace")
        dropTempColumn("hsa_id")
    }

    protected void addBatchEntries(Object input, String platform, String environment, BatchingPreparedStatementWrapper ps) {
        input.data.anropsbehorighet.each {
            def logicalAddress = it.relationships.logiskAdress
            def namespace = it.relationships.tjanstekontrakt
            def hsaId = it.relationships.tjanstekonsument

            boolean isNew = checkNotDuplicate(logicalAddress, hsaId, namespace, environment, platform)
            if (isNew) {
                ps.addBatch([environment, platform, logicalAddress, namespace, hsaId])
            }
        }
    }
}
