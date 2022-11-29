import groovy.sql.BatchingPreparedStatementWrapper
import groovy.sql.Sql
import java.text.SimpleDateFormat
import org.slf4j.Logger

public class TableConnectionPoint extends Table {

    private static final String SQL_CREATE_TABLE = multiline(
        "CREATE TABLE %TABLE% (",
        "   id bigint(20) NOT NULL AUTO_INCREMENT,",
        "   environment varchar(255) DEFAULT NULL,",
        "   platform varchar(255) DEFAULT NULL,",
        "   snapshot_time datetime DEFAULT NULL,",
        "   PRIMARY KEY (id)",
        ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET= utf8")

    private static final String SQL_INSERT = "insert into %TABLE% (platform, environment, snapshot_time)  values(?, ?, ?)"

    public TableConnectionPoint(Sql db, Logger logger, String tableSuffix) {
        super(db, logger, "connectionpoint", tableSuffix, SQL_CREATE_TABLE, SQL_INSERT)
    }

    public void addIndexes() {
        addKey("environment", "tmp_index_1")
        addKey("platform", "tmp_index_2")
    }

    public void removeTempColumnsAndIndex() {
        dropIndex("tmp_index_1")
        dropIndex("tmp_index_2")
    }

    protected void addBatchEntries(Object input, String platform, String environment, BatchingPreparedStatementWrapper ps) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"))
		Date exportTime = formatter.parse(input.tidpunkt)
            boolean isNew = checkNotDuplicate(platform, environment)
            if (isNew) {
                ps.addBatch([platform, environment, exportTime])
            }
    }
}
