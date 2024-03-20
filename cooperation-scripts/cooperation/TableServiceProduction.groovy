import groovy.sql.BatchingPreparedStatementWrapper
import groovy.sql.Sql
import org.slf4j.Logger

public class TableServiceProduction extends Table {

    private static final String SQL_CREATE_TABLE = multiline(
        "CREATE TABLE %TABLE% (",
        "   id bigint(20) NOT NULL AUTO_INCREMENT,",
        "   physical_address varchar(255) DEFAULT NULL,",
        "   rivta_profile varchar(255) DEFAULT NULL,",
        "   tmp_environment varchar(255) DEFAULT NULL,",
        "   tmp_platform varchar(255) DEFAULT NULL,",
        "   tmp_logical_address varchar(255) DEFAULT NULL,",
        "   tmp_namespace varchar(255) DEFAULT NULL,",
        "   tmp_hsa_id varchar(255) DEFAULT NULL,",
        "   PRIMARY KEY (id)",
        ") ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET= utf8")

    private static final String SQL_INSERT = "insert into %TABLE% (physical_address, rivta_profile, tmp_environment, tmp_platform, tmp_logical_address, tmp_namespace, tmp_hsa_id) values(?, ?, ?, ?, ?, ?, ?)"

    public TableServiceProduction(Sql db, Logger logger, String tableSuffix) {
        super(db, logger, "serviceproduction", tableSuffix, SQL_CREATE_TABLE, SQL_INSERT)
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
        addForeignKeyIdColumn("service_contract_id")
        addForeignKeyIdColumn("service_producer_id")
      
        addForeignKeyColumnData("connection_point_id", "connectionpoint", [["tmp_environment", "environment"], ["tmp_platform", "platform"]])
        addForeignKeyColumnData("logical_address_id", "logicaladdress", [["tmp_logical_address", "logical_address"]])
        addForeignKeyColumnData("service_contract_id", "servicecontract", [["tmp_namespace", "namespace"]])
        addForeignKeyColumnData("service_producer_id", "serviceproducer", [["tmp_hsa_id", "hsa_id"], ["tmp_environment", "tmp_environment"], ["tmp_platform", "tmp_platform"]])

        addForeignKeyConstraint("connection_point_id", "connectionpoint", "FK_serviceproduction_3")
        addForeignKeyConstraint("logical_address_id", "logicaladdress", "FK_serviceproduction_4")
        addForeignKeyConstraint("service_contract_id", "servicecontract", "FK_serviceproduction_1")
        addForeignKeyConstraint("service_producer_id", "serviceproducer", "FK_serviceproduction_2")

        addKey("connection_point_id", "IX_serviceproduction_1")
        addKey("logical_address_id", "IX_serviceproduction_2")
        addKey("service_contract_id", "IX_serviceproduction_3")
        addKey("service_producer_id", "IX_serviceproduction_4")
    }

    public void removeTempColumnsAndIndex() {
        dropTempColumn("environment")
        dropTempColumn("platform")
        dropTempColumn("logical_address")
        dropTempColumn("namespace")
        dropTempColumn("hsa_id")
    }

    protected void addBatchEntries(Object input, String platform, String environment, BatchingPreparedStatementWrapper ps) {
        input.data.vagval.each {
            def physicalAddress = it.relationships.anropsadress
            def rivtaProfile = it.relationships.rivtaProfil
            def logicalAddress = it.relationships.logiskadress
            def namespace = it.relationships.tjanstekontrakt
            def hsaId = it.relationships.tjansteproducent
            boolean isNew = checkNotDuplicate(logicalAddress, hsaId, namespace, environment, platform)
            if (isNew) {
                ps.addBatch([physicalAddress, rivtaProfile, environment, platform, logicalAddress, namespace, hsaId])
            }
        }
    }
}
