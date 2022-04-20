#!/usr/bin/env groovy
import groovy.io.FileType
import groovy.json.*
import groovy.sql.Sql
import groovy.transform.Field
import groovy.util.ConfigSlurper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat

@Grapes([
	@GrabConfig(systemClassLoader = true),
	@Grab(group = 'mysql', module = 'mysql-connector-java', version = '5.1.36'),
	@Grab(group = 'ch.qos.logback', module = 'logback-classic', version = '1.2.3'),
	@Grab(group = 'net.logstash.logback', module = 'logstash-logback-encoder', version = '6.4')
])

@Field
static final String DB_DRIVER = "com.mysql.jdbc.Driver"

@Field
static final String TABLE_SUFFIX = "_new"

// Logger object (log to stdout can be enabled in logback.groovy for easier debugging)
@Field
static final Logger logger = LoggerFactory.getLogger("scriptLogger")

def cli = new CliBuilder(
	usage: 'TakCooperationImport [options]',
	header: '\nAvailable options (use -h for help):\n')
cli.with
	{
		h longOpt: 'help', 'Usage Information', required: false
		_ longOpt: 'url', 'Connection URL \n eg. jdbc:h2:tcp://localhost/~/cooperation', args: 1, required: true
		u longOpt: 'user', 'User ID', args: 1, required: true
		p longOpt: 'password', 'Password', args: 1, required: false
		d longOpt: 'directory', 'Directory that holds data dump files', args: 1, required: false
	}

try {
	def opt = cli.parse(args)
	if (!opt) return
	if (opt.h) cli.usage()

	def url = opt.url
	def username = opt.u
	def password = opt.p ? opt.p : ''
	def dataDirectory = opt.d ? opt.d.replaceFirst("^~", System.getProperty("user.home")) : '.'

	importData(url, username, password, dataDirectory);

} catch (Exception e) {
	logger.error("Exception in TakCooperationImport.groovy", e)
	throw e
}

/**
 * Imports TAK data from TAK export JSON to cooperation database
 *
 * @param url Database URL, e.g. "jdbc:mysql://localhost:3306/testdb" or "jdbc:mysql://ind-dtjp-mysql1.ind1.sth.basefarm.net:3306/cooperation"
 * @param username Database username
 * @param password Database password
 * @param dataDirectory Path to the (local) folder containing the takdump*.json files that should be imported
 */
def importData(url, username, password, dataDirectory) {
	def db = Sql.newInstance(url, username, password, DB_DRIVER)

	// Read configuration
	final ConfigObject config = new ConfigSlurper().parse(new File("CoopConfig.groovy").toURI().toURL());
	logger.info("Begin: import files in dir: " + dataDirectory)

	def importer = new JsonImporter(db, logger, config.environments, dataDirectory, TABLE_SUFFIX)
	importer.importFromFiles()

	db.close();
	logger.info("DONE! Import all tak data to cooperation database")
}
