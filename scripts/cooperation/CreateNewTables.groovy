#!/usr/bin/env groovy

/**
 * Will create an empty version of all tables, suffixed _new 
 *
 */

@Grapes([
	@GrabConfig(systemClassLoader=true),
	@Grab(group='mysql', module='mysql-connector-java', version='5.1.36')
])

import groovy.sql.Sql

def cli = new CliBuilder(
	usage: 'CreateNewTables [options]',
	header: '\nAvailable options (use -h for help):\n')
cli.with
	{
		h longOpt: 'help', 'Usage Information', required: false
		url longOpt: 'url', 'Connection URL \n eg. jdbc:mysql://localhost/cooperation', args: 1, required: true
		u longOpt: 'user', 'User ID', args: 1, required: true
		p longOpt: 'password', 'Password', args: 1, required: false
	}

def opt = cli.parse(args)
if (!opt) return
if (opt.h) cli.usage()

def url = opt.url
def username = opt.u
def password = opt.p ? opt.p : ''

//Cooperation db settings
def db = Sql.newInstance(url, username, password, 'com.mysql.jdbc.Driver')

def random(){
	return String.valueOf(Math.random()).split("0.")[1]
}

println "START! Create _new tables in cooperation database"

db.execute "DROP TABLE IF EXISTS serviceproduction_new"
db.execute "DROP TABLE IF EXISTS cooperation_new"
db.execute "DROP TABLE IF EXISTS connectionpoint_new"
db.execute "DROP TABLE IF EXISTS serviceconsumer_new"
db.execute "DROP TABLE IF EXISTS servicecontract_new"
db.execute "DROP TABLE IF EXISTS serviceproducer_new"
db.execute "DROP TABLE IF EXISTS logicaladdress_new"
db.execute "DROP TABLE IF EXISTS servicedomain_new"

db.execute "CREATE TABLE connectionpoint_new (  \
  id bigint(20) NOT NULL AUTO_INCREMENT, \
  environment varchar(255) DEFAULT NULL, \
  platform varchar(255) DEFAULT NULL, \
  snapshot_time datetime DEFAULT NULL, \
  PRIMARY KEY (id) \
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8"

db.execute "CREATE TABLE logicaladdress_new (  \
  id bigint(20) NOT NULL AUTO_INCREMENT,  \
  description varchar(255) DEFAULT NULL,  \
  logical_address varchar(255) DEFAULT NULL,   \
  PRIMARY KEY (id),  \
  UNIQUE KEY UK_logicaladdress_1 (logical_address)  \
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET= utf8"

db.execute "CREATE TABLE serviceconsumer_new (  \
  id bigint(20) NOT NULL AUTO_INCREMENT,  \
  description varchar(255) DEFAULT NULL,  \
  hsa_id varchar(255) DEFAULT NULL,  \
  PRIMARY KEY (id),  \
  UNIQUE KEY UK_serviceconsumer_1 (hsa_id)  \
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET= utf8"


db.execute "CREATE TABLE servicedomain_new (  \
  id bigint(20) NOT NULL AUTO_INCREMENT,  \
  name varchar(255) DEFAULT NULL,  \
  namespace varchar(255) DEFAULT NULL,  \
  PRIMARY KEY (id),  \
  UNIQUE KEY UK_servicedomain_1 (namespace)  \
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET= utf8"

db.execute "CREATE TABLE servicecontract_new (  \
  id bigint(20) NOT NULL AUTO_INCREMENT,  \
  major int(11) DEFAULT NULL,  \
  minor int(11) DEFAULT NULL,  \
  name varchar(255) DEFAULT NULL,  \
  namespace varchar(255) DEFAULT NULL,  \
  service_domain_id bigint(20) DEFAULT NULL,  \
  PRIMARY KEY (id),  \
  CONSTRAINT FK_servicecontract_1" + random() +" FOREIGN KEY (service_domain_id) REFERENCES servicedomain_new (id),  \
  UNIQUE KEY UK_servicecontract_1 (namespace)  \
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET= utf8"

db.execute "CREATE TABLE serviceproducer_new (  \
  id bigint(20) NOT NULL AUTO_INCREMENT,  \
  description varchar(255) DEFAULT NULL,  \
  hsa_id varchar(255) DEFAULT NULL,  \
  PRIMARY KEY (id),  \
  UNIQUE KEY UK_serviceproducer_1 (hsa_id)  \
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET= utf8"


db.execute "CREATE TABLE serviceproduction_new (  \
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
  CONSTRAINT FK_serviceproduction_1" + random() +" FOREIGN KEY (service_contract_id) REFERENCES servicecontract_new (id),  \
  CONSTRAINT FK_serviceproduction_2" + random() +" FOREIGN KEY (service_producer_id) REFERENCES serviceproducer_new (id),  \
  CONSTRAINT FK_serviceproduction_3" + random() +" FOREIGN KEY (connection_point_id) REFERENCES connectionpoint_new (id),  \
  CONSTRAINT FK_serviceproduction_4" + random() +" FOREIGN KEY (logical_address_id) REFERENCES logicaladdress_new (id)  \
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET= utf8"


db.execute "CREATE TABLE cooperation_new (  \
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
  CONSTRAINT FK_cooperation_1" + random() +" FOREIGN KEY (service_consumer_id) REFERENCES serviceconsumer_new (id),  \
  CONSTRAINT FK_cooperation_2" + random() +" FOREIGN KEY (connection_point_id) REFERENCES connectionpoint_new (id),  \
  CONSTRAINT FK_cooperation_3" + random() +" FOREIGN KEY (logical_address_id) REFERENCES logicaladdress_new (id),  \
  CONSTRAINT FK_cooperation_4" + random() +" FOREIGN KEY (service_contract_id) REFERENCES servicecontract_new (id)   \
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET= utf8"


println "******* END  *****************************************************"
println 'Timestamp finishing: ' + new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'", TimeZone.getTimeZone("UTC"))
println '************************************************************'

db.close();

println ''
println 'DONE! Create _new tables in cooperation database'
println ''
