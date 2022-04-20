import groovy.sql.BatchingPreparedStatementWrapper
import groovy.sql.Sql
import org.slf4j.Logger

public class TableServiceProducer extends Table {

    private static final String SQL_CREATE_TABLE = multiline(
        "CREATE TABLE %TABLE% (",
        "   id bigint(20) NOT NULL AUTO_INCREMENT,",
        "   description varchar(255) DEFAULT NULL,",
        "   hsa_id varchar(255) DEFAULT NULL,",
        "   tmp_environment varchar(255) DEFAULT NULL,",
        "   tmp_platform varchar(255) DEFAULT NULL,",
        "   PRIMARY KEY (id)",
        ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET= utf8")

    private static final String SQL_INSERT = "insert into %TABLE% (description, hsa_id, tmp_environment, tmp_platform) values(?, ?, ?, ?)"

    public TableServiceProducer(Sql db, Logger logger, String tableSuffix) {
        super(db, logger, "serviceproducer", tableSuffix, SQL_CREATE_TABLE, SQL_INSERT)
    }

    public void addIndexes() {
        addKey("hsa_id", "tmp_index_1")        
        addKey("tmp_environment", "tmp_index_2")        
        addKey("tmp_platform", "tmp_index_3")
    }

    public void addForeignKeys() {
        addForeignKeyIdColumn("connection_point_id")
        addForeignKeyColumnData("connection_point_id", "connectionpoint", [["tmp_environment", "environment"], ["tmp_platform", "platform"]])
        addForeignKeyConstraint("connection_point_id", "connectionpoint", "FK_serviceproducer_1")
        addKey("connection_point_id", "IX_serviceproducer_1")
    }

    public void removeTempColumnsAndIndex() {
        dropTempColumn("environment")
        dropTempColumn("platform")
        dropIndex("tmp_index_1")
    }

    protected void addBatchEntries(Object input, String platform, String environment, BatchingPreparedStatementWrapper ps) {
        input.data.tjansteproducent.each {
            def description = it.beskrivning
            def hsaId = it.hsaId
            boolean isNew = checkNotDuplicate(hsaId, environment, platform)
            if (isNew) {
                ps.addBatch([description, hsaId, environment, platform])
            }
        }
    }
}
