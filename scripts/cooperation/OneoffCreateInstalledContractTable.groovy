#!/usr/bin/env groovy

/**
 * Will create two versions of the new table table
 *
 */

@Grapes([
	@GrabConfig(systemClassLoader=true),
	@Grab(group='mysql', module='mysql-connector-java', version='8.0.29')
])

import groovy.sql.Sql
import groovy.cli.commons.CliBuilder

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
def db = Sql.newInstance(url, username, password, 'com.mysql.cj.jdbc.Driver')

def random(){
	return String.valueOf(Math.random()).split("0.")[1]
}

println "START! Create installedcontract tables in cooperation database"

db.execute "DROP TABLE IF EXISTS installedcontract_old"
db.execute "DROP TABLE IF EXISTS installedcontract"


db.execute "CREATE TABLE installedcontract (  \
  id bigint(20) NOT NULL AUTO_INCREMENT,  \
  connection_point_id bigint(20) DEFAULT NULL,  \
  service_contract_id bigint(20) DEFAULT NULL,  \
  PRIMARY KEY (id),  \
  KEY IX_installedcontract_1 (connection_point_id),  \
  KEY IX_installedcontract_2 (service_contract_id),  \
  CONSTRAINT FK_installedcontract_1" + random() +" FOREIGN KEY (connection_point_id) REFERENCES connectionpoint (id),  \
  CONSTRAINT FK_installedcontract_2" + random() +" FOREIGN KEY (service_contract_id) REFERENCES servicecontract (id)   \
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET= utf8"

db.execute "CREATE TABLE installedcontract_old (  \
  id bigint(20) NOT NULL AUTO_INCREMENT,  \
  connection_point_id bigint(20) DEFAULT NULL,  \
  service_contract_id bigint(20) DEFAULT NULL,  \
  PRIMARY KEY (id),  \
  KEY IX_installedcontract_1 (connection_point_id),  \
  KEY IX_installedcontract_2 (service_contract_id),  \
  CONSTRAINT FK_installedcontract_1" + random() +" FOREIGN KEY (connection_point_id) REFERENCES connectionpoint_old (id),  \
  CONSTRAINT FK_installedcontract_2" + random() +" FOREIGN KEY (service_contract_id) REFERENCES servicecontract_old (id)   \
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET= utf8"

println "******* END  *****************************************************"
println 'Timestamp finishing: ' + new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'", TimeZone.getTimeZone("UTC"))
println '************************************************************'

db.close();

println ''
println 'DONE! Create tables in cooperation database'
println ''
