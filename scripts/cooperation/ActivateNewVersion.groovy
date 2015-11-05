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

println "START! Rename tables in cooperation database"

db.execute "DROP TABLE IF EXISTS serviceproduction_old"
db.execute "DROP TABLE IF EXISTS cooperation_old"
db.execute "DROP TABLE IF EXISTS connectionpoint_old"
db.execute "DROP TABLE IF EXISTS serviceconsumer_old"
db.execute "DROP TABLE IF EXISTS servicecontract_old"
db.execute "DROP TABLE IF EXISTS serviceproducer_old"
db.execute "DROP TABLE IF EXISTS logicaladdress_old"
db.execute "DROP TABLE IF EXISTS servicedomain_old"

db.execute "ALTER TABLE cooperation.serviceproduction RENAME TO  cooperation.serviceproduction_old"
db.execute "ALTER TABLE cooperation.cooperation RENAME TO  cooperation.cooperation_old"
db.execute "ALTER TABLE cooperation.connectionpoint RENAME TO  cooperation.connectionpoint_old"
db.execute "ALTER TABLE cooperation.serviceconsumer RENAME TO  cooperation.serviceconsumer_old"
db.execute "ALTER TABLE cooperation.servicecontract RENAME TO  cooperation.servicecontract_old"
db.execute "ALTER TABLE cooperation.serviceproducer RENAME TO  cooperation.serviceproducer_old"
db.execute "ALTER TABLE cooperation.logicaladdress RENAME TO  cooperation.logicaladdress_old"
db.execute "ALTER TABLE cooperation.servicedomain RENAME TO  cooperation.servicedomain_old"

db.execute "ALTER TABLE cooperation.serviceproduction_new RENAME TO  cooperation.serviceproduction"
db.execute "ALTER TABLE cooperation.cooperation_new RENAME TO  cooperation.cooperation"
db.execute "ALTER TABLE cooperation.connectionpoint_new RENAME TO  cooperation.connectionpoint"
db.execute "ALTER TABLE cooperation.serviceconsumer_new RENAME TO  cooperation.serviceconsumer"
db.execute "ALTER TABLE cooperation.servicecontract_new RENAME TO  cooperation.servicecontract"	
db.execute "ALTER TABLE cooperation.serviceproducer_new RENAME TO  cooperation.serviceproducer"
db.execute "ALTER TABLE cooperation.logicaladdress_new RENAME TO  cooperation.logicaladdress"
db.execute "ALTER TABLE cooperation.servicedomain_new RENAME TO  cooperation.servicedomain"


println "******* END  *****************************************************"
println 'Timestamp finishing: ' + new Date().format("yyyy-MM-dd'T'HH:mm:ss", TimeZone.getTimeZone("UTC"))
println '************************************************************'

db.close();

println ''
println 'DONE! renamed tables in cooperation database'
println ''
