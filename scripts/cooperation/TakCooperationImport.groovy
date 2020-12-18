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
	@Grab(group = 'com.h2database', module = 'h2', version = '1.4.187'),
	@Grab(group = 'org.hsqldb', module = 'hsqldb', version = '2.3.3'),
	@Grab(group = 'mysql', module = 'mysql-connector-java', version = '5.1.36'),
	@Grab(group = 'ch.qos.logback', module = 'logback-classic', version = '1.2.3'),
	@Grab(group = 'net.logstash.logback', module = 'logstash-logback-encoder', version = '6.4')
])

/**
 * Imports TAK data from TAK export JSON to cooperation database
 *
 * 1, Start your local H2 database and make sure cooperation database and tables exist
 * 2, Make sure you have exported data from TAK using TakCooperationExport.groovy
 * 3, Update the database config in this script
 * 4, Run this groovy script by $groovy TakCooperationImport.groovy > importLog.txt
 */
@Field
static Logger logger = LoggerFactory.getLogger("scriptLogger")

def cli = new CliBuilder(
	usage: 'TakCooperationImport [options]',
	header: '\nAvailable options (use -h for help):\n')
cli.with
	{
		h longOpt: 'help', 'Usage Information', required: false
		_ longOpt: 'url', 'Connection URL \n eg. jdbc:h2:tcp://localhost/~/cooperation', args: 1, required: true
		u longOpt: 'user', 'User ID', args: 1, required: true
		p longOpt: 'password', 'Password', args: 1, required: false
		_ longOpt: 'clear', 'Clear database before importing', required: false
		d longOpt: 'directory', 'Directory that holds data dump files', args: 1, required: false
	}

try {
	def opt = cli.parse(args)
	if (!opt) return
	if (opt.h) cli.usage()

	def url = opt.url
	def username = opt.u
	def password = opt.p ? opt.p : ''
	def clear = opt.clear
	def dataDirectory = opt.d ? opt.d.replaceFirst("^~", System.getProperty("user.home")) : '.'

	importData(url, username, password, dataDirectory, clear);

} catch (Exception e) {
	logger.error("Exception in TakCooperationImport.groovy", e)
	throw e
}


def importData(url, username, password, dataDirectory, clear) {
	//Cooperation db settings
	def db = Sql.newInstance(url, username, password, 'com.mysql.jdbc.Driver')

	if (clear) clearDatabase(db)

	logger.info("START! Importing all tak data to cooperation database")

	// Read configuration
	final ConfigObject config = new ConfigSlurper().parse(new File("CoopConfig.groovy").toURI().toURL());
	logger.info("Begin: import files in dir: " + dataDirectory)
	def directory = new File(dataDirectory)

	config.environments.each {
		envs ->
			logger.info("Environment: ${envs}")
			directory.eachFileMatch(FileType.FILES, ~".*${envs}\\.json") { it ->
				logger.info("Begin: import file: " + it.name)
				importFil(it, db)
				logger.info("End: import file: " + it.name)
			}
	}
	db.close();
	logger.info("DONE! Import all tak data to cooperation database")
}

def importFil(fil, db) {
	try {
		//Extract env and platform from file name with convention takdump_platform_environment.json
		def fileName = fil.name.replaceFirst(~/\.[^\.]+$/, '')
		def platform = fileName.split('_')[1].toUpperCase()
		def environment = fileName.split('_')[2].toUpperCase()

		def inputJSON = new JsonSlurper().parseText(fil.text)

		logger.info("START IMPORT FILE $fil.name")
		logger.info("Format version: $inputJSON.formatVersion")
		logger.info("Import from platform: $platform and environment: $environment")

		def formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		def snapshotTime = formatter.parse(inputJSON.tidpunkt);

		logger.info("INFO: Processing connectionPoints")
		connectionPoint(db, platform, environment, snapshotTime)
		logger.info("INFO: Processing logicalAddress")
		logicalAddress(db, inputJSON)
		logger.info("INFO: Processing serviceDomain")
		serviceDomain(db, inputJSON)
		logger.info("INFO: Processing serviceContract")
		serviceContract(db, inputJSON)
		logger.info("INFO: Processing serviceConsumer")
		serviceConsumer(db, inputJSON, platform, environment)
		logger.info("INFO: Processing serviceProducer")
		serviceProducer(db, inputJSON, platform, environment)
		logger.info("INFO: Processing cooperation")
		cooperation(db, inputJSON, platform, environment)
		logger.info("INFO: Processing serviceProduction")
		serviceProduction(db, inputJSON, platform, environment)
		logger.info("INFO: Processing installedContract")
		installedContract(db, inputJSON, platform, environment)
		logger.info("END IMPORT FILE $fil.name")
	} catch (Exception e) {
		logger.error("Exception: import fil: " + fil.name, e)
	}
}


def countRows = { description, table ->
	def result = db.firstRow("SELECT COUNT(*) AS numberOfRows FROM " + table)
	logger.info("$table, $description, rows: $result.numberOfRows")
}

def connectionPoint(db, platform, environment, exportTime) {
	if (db.firstRow("SELECT * FROM connectionpoint_new WHERE platform = $platform AND environment = $environment") == null) {
		db.executeInsert "insert into connectionpoint_new(platform, environment, snapshot_time)  values($platform, $environment, $exportTime)"
	} else {
		logger.info("INFO: Connectionpoint platform: $platform, environment: $environment already exist")
	}
}

def logicalAddress(db, inputJSON) {
	inputJSON.data.logiskadress.each {
		if (db.firstRow("SELECT * FROM logicaladdress_new WHERE logical_address = $it.hsaId") == null) {
			db.executeInsert "insert into logicaladdress_new(logical_address,description)  values($it.hsaId, $it.beskrivning)"
		} else {
			logger.info("INFO: Logical address $it already exist")
		}
	}
}

def serviceContract(db, inputJSON) {
	inputJSON.data.tjanstekontrakt.each {

		def domain = domain(it.namnrymd)
		if (db.firstRow("SELECT * FROM servicecontract_new WHERE namespace = $it.namnrymd") == null) {
			db.executeInsert "insert into servicecontract_new(major,minor,name, namespace, service_domain_id) \
                    select $it.majorVersion, $it.minorVersion, $it.beskrivning, $it.namnrymd, c.id from \
                    (SELECT id FROM servicedomain_new WHERE namespace = $domain ) as c"
		} else {
			logger.info("INFO: Servicecontract $it already exist")
		}
	}
}

def serviceDomain(db, inputJSON) {
	inputJSON.data.tjanstekontrakt.each {

		def domainrymd = domain(it.namnrymd)
		if (db.firstRow("SELECT * FROM servicedomain_new WHERE namespace = $domainrymd") == null) {
			db.executeInsert "insert into servicedomain_new(name, namespace)  values('namn', $domainrymd)"
		} else {
			logger.info("INFO: Servicedomain $it already exist")
		}
	}
}

def serviceConsumer(db, inputJSON, platform, environment) {
	inputJSON.data.tjanstekonsument.each {

		if (db.firstRow(
			"SELECT * FROM serviceconsumer_new s, connectionpoint_new cp \
		    WHERE s.connection_point_id = cp.id \
		    AND s.hsa_id = $it.hsaId \
		    AND cp.environment = $environment \
            AND cp.platform = $platform") == null) {
			db.executeInsert      \
			             "insert into serviceconsumer_new(hsa_id, description, connection_point_id) \
			         select $it.hsaId, $it.beskrivning, c.id  \
			         from (SELECT id FROM connectionpoint_new WHERE platform = $platform AND environment = $environment) as c"        \

		} else {
			logger.info("INFO: Serviceconsumer $it already exist")
		}
	}
}

def serviceProducer(db, inputJSON, platform, environment) {
	inputJSON.data.tjansteproducent.each {

		if (db.firstRow(
			"SELECT * FROM serviceproducer_new s, connectionpoint_new cp \
		    WHERE s.connection_point_id = cp.id \
		    AND s.hsa_id = $it.hsaId \
		    AND cp.environment = $environment \
            AND cp.platform = $platform") == null) {
			db.executeInsert      \
			             "insert into serviceproducer_new(hsa_id, description, connection_point_id) \
			         select $it.hsaId, $it.beskrivning, c.id  \
			         from (SELECT id FROM connectionpoint_new WHERE platform = $platform AND environment = $environment) as c"        \

		} else {
			logger.info("INFO: Serviceproducer $it already exist")
		}
	}
}

def cooperation(db, inputJSON, platform, environment) {
	inputJSON.data.anropsbehorighet.each {

		if (db.firstRow(
			"SELECT * FROM cooperation_new c, logicaladdress_new l, serviceconsumer_new s, servicecontract_new sc, connectionpoint_new cp \
                WHERE c.logical_address_id = l.id \
                AND c.service_consumer_id = s.id \
                AND c.service_contract_id = sc.id \
                AND c.connection_point_id = cp.id \
                AND s.connection_point_id = cp.id \
                AND l.logical_address = $it.relationships.logiskAdress \
                AND s.hsa_id = $it.relationships.tjanstekonsument \
                AND sc.namespace = $it.relationships.tjanstekontrakt \
                AND cp.environment = $environment \
                AND cp.platform = $platform") == null) {

			db.executeInsert      \
                     "insert into cooperation_new(connection_point_id, logical_address_id, service_consumer_id, service_contract_id) \
                    select c.id, address.id, consumer.id, contract.id \
                    from \
                        (SELECT id FROM connectionpoint_new WHERE platform = $platform AND environment = $environment) as c, \
                        (SELECT id FROM logicaladdress_new WHERE logical_address = $it.relationships.logiskAdress) as address, \
                        (SELECT s.id FROM serviceconsumer_new s, connectionpoint_new cp WHERE s.hsa_id = $it.relationships.tjanstekonsument \
                                   AND s.connection_point_id = cp.id \
                                   AND cp.environment = $environment \
                                   AND cp.platform = $platform ) as consumer, \
                        (SELECT id FROM servicecontract_new WHERE namespace = $it.relationships.tjanstekontrakt) as contract"
		} else {
			logger.info("INFO: Cooperation for serviceconsumer $it already exist")
		}
	}
}

def installedContract(db, inputJSON, platform, environment) {
	inputJSON.data.tjanstekontrakt.each {

		if (db.firstRow(
			"SELECT * FROM installedcontract_new ic, servicecontract_new sc, connectionpoint_new cp \
                WHERE ic.service_contract_id = sc.id \
                AND ic.connection_point_id = cp.id \
                AND sc.namespace = $it.namnrymd \
                AND cp.environment = $environment \
                AND cp.platform = $platform") == null) {

			db.executeInsert      \
                     "insert into installedcontract_new(connection_point_id, service_contract_id) \
                    select c.id, contract.id \
                    from \
                        (SELECT id FROM connectionpoint_new WHERE platform = $platform AND environment = $environment) as c, \
                         (SELECT id FROM servicecontract_new WHERE namespace = $it.namnrymd) as contract"
		} else {
			logger.info("INFO: InstalledContract_new for serviceContract $it already exist")
		}
	}
}

def serviceProduction(db, inputJSON, platform, environment) {
	inputJSON.data.vagval.each {

		if (db.firstRow(
			"SELECT * FROM serviceproduction_new c, logicaladdress_new l, serviceproducer_new s, servicecontract_new sc, connectionpoint_new cp \
                WHERE c.logical_address_id = l.id \
                AND c.service_producer_id = s.id \
                AND c.service_contract_id = sc.id \
                AND c.connection_point_id = cp.id \
                AND s.connection_point_id = cp.id \
                AND l.logical_address = $it.relationships.logiskadress \
                AND s.hsa_id = $it.relationships.tjansteproducent \
                AND sc.namespace = $it.relationships.tjanstekontrakt \
                AND cp.environment = $environment \
                AND cp.platform = $platform") == null) {

			db.executeInsert      \
                     "insert into serviceproduction_new(physical_address, rivta_profile, connection_point_id, logical_address_id, service_producer_id, service_contract_id) \
                    select $it.relationships.anropsadress, $it.relationships.rivtaProfil, c.id, address.id, producer.id, contract.id \
                    from \
                        (SELECT id FROM connectionpoint_new WHERE platform = $platform AND environment = $environment) as c, \
                        (SELECT id FROM logicaladdress_new WHERE logical_address = $it.relationships.logiskadress) as address, \
                        (SELECT s.id FROM serviceproducer_new s, connectionpoint_new cp WHERE s.hsa_id = $it.relationships.tjansteproducent \
                                   AND s.connection_point_id = cp.id \
                                   AND cp.environment = $environment \
                                   AND cp.platform = $platform ) as producer, \
                        (SELECT id FROM servicecontract_new WHERE namespace = $it.relationships.tjanstekontrakt) as contract"
		} else {
			logger.info("INFO: Serviceproduction already exist $it")
		}
	}
}

def domain(namespace) {

	def temp = namespace.replaceFirst('urn:riv:', '')
	def domain = temp.split(':[A-Z]')[0]
	return domain
}


def clearDatabase(db) {
	logger.info("START! Clearing database")
	db.execute 'SET REFERENTIAL_INTEGRITY FALSE'
	db.execute "delete from connectionpoint"
	db.execute "delete from cooperation"
	db.execute "delete from logicaladdress"
	db.execute "delete from serviceconsumer"
	db.execute "delete from servicecontract"
	db.execute "delete from serviceproducer"
	db.execute "delete from serviceproduction"
	db.execute "delete from servicedomain"
	db.execute "delete from installedcontract"
	db.execute 'SET REFERENTIAL_INTEGRITY TRUE'
	logger.info("Database is cleared")
}

