#!/usr/bin/env groovy

/**
 * Will create an empty version of all tables, suffixed _new
 *
 * This is no longer used by cooperation-import-from-tak.sh
 * (left here in case someone wants to create tables manually for some reason)
 */
@Grapes([
	@GrabConfig(systemClassLoader=true),
	@Grab(group='mysql', module='mysql-connector-java', version='8.0.29'),
	@Grab(group = 'ch.qos.logback', module = 'logback-classic', version = '1.5.12'),
	@Grab(group = 'net.logstash.logback', module = 'logstash-logback-encoder', version='6.4')
])

import groovy.transform.Field
import groovy.sql.Sql
import groovy.cli.commons.CliBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Field
static Logger logger = LoggerFactory.getLogger("scriptLogger")


def cli = new CliBuilder(
	usage: 'CreateNewTables [options]',
	header: '\nAvailable options (use -h for help):\n')

cli.with
	{
		h longOpt: 'help', 'Usage Information', required: false
		url longOpt: 'url', 'Connection URL \n eg. jdbc:mysql://localhost/cooperation', args: 1, required: true
		u longOpt: 'user', 'User ID', args: 1, required: true
		p longOpt: 'password', 'Password', args: 1, required: false
		s longOpt: 'suffix', 'Suffix', args: 1, required: false
	}

def tstamp(){
	return new Date().format( 'ddHHmm' )
}

def random(){
	return String.valueOf(Math.random()).split("0\\.")[1]
}

try {
	def opt = cli.parse(args)
	if (!opt) return
	if (opt.h) cli.usage()

	def url = opt.url
	def username = opt.u
	def password = opt.p ? opt.p : ''
	def suffix = opt.s ? opt.s : ''

	createTables(url, username, password, suffix);

} catch (Exception e) {
	logger.error("Exception in CreateNewTables.groovy", e)
	throw e
}

def createTables(String url, String username, String password, String suffix) {
	//Cooperation db settings
	def db = Sql.newInstance(url, username, password, 'com.mysql.cj.jdbc.Driver')

	logger.info("START! Create " + suffix + " tables in cooperation database")

	db.execute "DROP TABLE IF EXISTS serviceproduction" + suffix
	db.execute "DROP TABLE IF EXISTS cooperation" + suffix
	db.execute "DROP TABLE IF EXISTS installedcontract" + suffix
	db.execute "DROP TABLE IF EXISTS serviceconsumer" + suffix
	db.execute "DROP TABLE IF EXISTS servicecontract" + suffix
	db.execute "DROP TABLE IF EXISTS serviceproducer" + suffix
	db.execute "DROP TABLE IF EXISTS connectionpoint" + suffix
	db.execute "DROP TABLE IF EXISTS logicaladdress" + suffix
	db.execute "DROP TABLE IF EXISTS servicedomain" + suffix

	db.execute "CREATE TABLE connectionpoint" + suffix + " (  \
  id bigint(20) NOT NULL AUTO_INCREMENT, \
  environment varchar(255) DEFAULT NULL, \
  platform varchar(255) DEFAULT NULL, \
  snapshot_time datetime DEFAULT NULL, \
  PRIMARY KEY (id) \
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8"

	db.execute "CREATE TABLE logicaladdress" + suffix + " (  \
  id bigint(20) NOT NULL AUTO_INCREMENT,  \
  description varchar(255) DEFAULT NULL,  \
  logical_address varchar(255) DEFAULT NULL,   \
  PRIMARY KEY (id),  \
  UNIQUE KEY UK_logicaladdress_1 (logical_address)  \
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET= utf8"

	db.execute "CREATE TABLE serviceconsumer" + suffix + " (  \
  id bigint(20) NOT NULL AUTO_INCREMENT,  \
  description varchar(255) DEFAULT NULL,  \
  hsa_id varchar(255) DEFAULT NULL,  \
  connection_point_id bigint(20) DEFAULT NULL,  \
  PRIMARY KEY (id),  \
  KEY IX_serviconsumer_1 (connection_point_id),  \
  CONSTRAINT FK_serviceconsumer_1" + tstamp() + " FOREIGN KEY (connection_point_id) REFERENCES connectionpoint" + suffix + " (id)  \
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET= utf8"


	db.execute "CREATE TABLE servicedomain" + suffix + " (  \
  id bigint(20) NOT NULL AUTO_INCREMENT,  \
  name varchar(255) DEFAULT NULL,  \
  namespace varchar(255) DEFAULT NULL,  \
  PRIMARY KEY (id),  \
  UNIQUE KEY UK_servicedomain_1 (namespace)  \
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET= utf8"

	db.execute "CREATE TABLE servicecontract" + suffix + " (  \
  id bigint(20) NOT NULL AUTO_INCREMENT,  \
  major int(11) DEFAULT NULL,  \
  minor int(11) DEFAULT NULL,  \
  name varchar(255) DEFAULT NULL,  \
  namespace varchar(255) DEFAULT NULL,  \
  service_domain_id bigint(20) DEFAULT NULL,  \
  PRIMARY KEY (id),  \
  CONSTRAINT FK_servicecontract_1" + tstamp() + " FOREIGN KEY (service_domain_id) REFERENCES servicedomain" + suffix + " (id),  \
  UNIQUE KEY UK_servicecontract_1 (namespace)  \
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET= utf8"

	db.execute "CREATE TABLE serviceproducer" + suffix + " (  \
  id bigint(20) NOT NULL AUTO_INCREMENT,  \
  description varchar(255) DEFAULT NULL,  \
  hsa_id varchar(255) DEFAULT NULL,  \
  connection_point_id bigint(20) DEFAULT NULL,  \
  PRIMARY KEY (id),  \
  KEY IX_serviceproducer_1 (connection_point_id),  \
  CONSTRAINT FK_serviceproducer_1" + tstamp() + " FOREIGN KEY (connection_point_id) REFERENCES connectionpoint" + suffix + " (id)  \
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET= utf8"


	db.execute "CREATE TABLE serviceproduction" + suffix + " (  \
  id bigint(20) NOT NULL AUTO_INCREMENT,  \
  physical_address varchar(255) DEFAULT NULL,  \
  rivta_profile varchar(255) DEFAULT NULL,  \
  connection_point_id bigint(20) DEFAULT NULL,  \
  logical_address_id bigint(20) DEFAULT NULL,  \
  service_contract_id bigint(20) DEFAULT NULL,  \
  service_producer_id bigint(20) DEFAULT NULL,  \
  PRIMARY KEY (id),  \
  KEY IX_serviceproduction_1 (connection_point_id),  \
  KEY IX_serviceproduction_2 (logical_address_id),  \
  KEY IX_serviceproduction_3 (service_contract_id),  \
  KEY IX_serviceproduction_4 (service_producer_id),  \
  CONSTRAINT FK_serviceproduction_1" + tstamp() + " FOREIGN KEY (service_contract_id) REFERENCES servicecontract" + suffix + " (id),  \
  CONSTRAINT FK_serviceproduction_2" + tstamp() + " FOREIGN KEY (service_producer_id) REFERENCES serviceproducer" + suffix + " (id),  \
  CONSTRAINT FK_serviceproduction_3" + tstamp() + " FOREIGN KEY (connection_point_id) REFERENCES connectionpoint" + suffix + " (id),  \
  CONSTRAINT FK_serviceproduction_4" + tstamp() + " FOREIGN KEY (logical_address_id) REFERENCES logicaladdress" + suffix + " (id)  \
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET= utf8"


	db.execute "CREATE TABLE cooperation" + suffix + " (  \
  id bigint(20) NOT NULL AUTO_INCREMENT,  \
  connection_point_id bigint(20) DEFAULT NULL,  \
  logical_address_id bigint(20) DEFAULT NULL,  \
  service_consumer_id bigint(20) DEFAULT NULL,  \
  service_contract_id bigint(20) DEFAULT NULL,  \
  PRIMARY KEY (id),  \
  KEY IX_cooperation_1 (connection_point_id),  \
  KEY IX_cooperation_2 (logical_address_id),  \
  KEY IX_cooperation_3 (service_consumer_id),  \
  KEY IX_cooperation_4 (service_contract_id),  \
  CONSTRAINT FK_cooperation_1" + tstamp() + " FOREIGN KEY (service_consumer_id) REFERENCES serviceconsumer" + suffix + " (id),  \
  CONSTRAINT FK_cooperation_2" + tstamp() + " FOREIGN KEY (connection_point_id) REFERENCES connectionpoint" + suffix + " (id),  \
  CONSTRAINT FK_cooperation_3" + tstamp() + " FOREIGN KEY (logical_address_id) REFERENCES logicaladdress" + suffix + " (id),  \
  CONSTRAINT FK_cooperation_4" + tstamp() + " FOREIGN KEY (service_contract_id) REFERENCES servicecontract" + suffix + " (id)   \
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET= utf8"

	db.execute "CREATE TABLE installedcontract" + suffix + " (  \
  id bigint(20) NOT NULL AUTO_INCREMENT,  \
  connection_point_id bigint(20) DEFAULT NULL,  \
  service_contract_id bigint(20) DEFAULT NULL,  \
  PRIMARY KEY (id),  \
  KEY IX_installedcontract_1 (connection_point_id),  \
  KEY IX_installedcontract_2 (service_contract_id),  \
  CONSTRAINT FK_installedcontract_1" + tstamp() + " FOREIGN KEY (connection_point_id) REFERENCES connectionpoint" + suffix + " (id),  \
  CONSTRAINT FK_installedcontract_2" + tstamp() + " FOREIGN KEY (service_contract_id) REFERENCES servicecontract" + suffix + " (id)   \
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET= utf8"

	db.close();

	logger.info("DONE! Create " + suffix + " tables in cooperation database")
}
