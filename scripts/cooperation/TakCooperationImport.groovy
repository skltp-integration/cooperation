#!/usr/bin/env groovy
import groovy.json.*
import groovy.sql.Sql
import groovy.cli.commons.CliBuilder
import groovy.transform.Field
import groovy.util.ConfigSlurper
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Grapes([
	@GrabConfig(systemClassLoader = true),
	@Grab(group = 'mysql', module = 'mysql-connector-java', version = '8.0.29'),
	@Grab(group = 'ch.qos.logback', module = 'logback-classic', version = '1.5.16'),
	@Grab(group = 'net.logstash.logback', module = 'logstash-logback-encoder', version='8.0'),
	@Grab(group = 'co.elastic.logging',   module = 'logback-ecs-encoder', version = '1.6.0'),
])

@Field
final String DB_DRIVER = "com.mysql.cj.jdbc.Driver"

@Field
final String TABLE_SUFFIX = "_new"

@Field
final Logger logger = LoggerFactory.getLogger("scriptLogger")

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
		e longOpt: 'environments', 'Comma-separated list of environments eg. ntjp_qa,ntjp_prod', args: 1, required: false
	}

try {
	def opt = cli.parse(args)
	if (!opt) return
	if (opt.h) cli.usage()

	def url = opt.url
	def username = opt.u
	def password = opt.p ? opt.p : ''
	def dataDirectory = opt.d ? opt.d.replaceFirst("^~", System.getProperty("user.home")) : '.'

	ConfigObject config = new ConfigSlurper().parse(new File("CoopConfig.groovy").toURI().toURL());
	List<String> environments = opt.e ? opt.e.tokenize(',') : config.environments

	importData(url, username, password, dataDirectory, environments);

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
 * @param List<String> of environments (format "platform_env")
 */
def importData(url, username, password, dataDirectory, environments) {
	def db = Sql.newInstance(url, username, password, DB_DRIVER)

	logger.info("Begin: import files in dir: " + dataDirectory)

	def importer = new JsonImporter(db, logger, environments, dataDirectory, TABLE_SUFFIX)
	importer.importFromFiles()

	db.close();
	logger.info("DONE! Import all tak data to cooperation database")
}
