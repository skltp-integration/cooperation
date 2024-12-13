#!/usr/bin/env groovy

@Grapes([
	    @Grab(group = 'ch.qos.logback', module = 'logback-classic', version = '1.5.12'),
		@Grab(group = 'net.logstash.logback', module = 'logstash-logback-encoder', version='8.0'),
		@Grab(group = 'co.elastic.logging', module = 'logback-ecs-encoder', version='1.6.0'),
		@GrabConfig(systemClassLoader = true)
])

import groovy.json.JsonSlurper
import groovy.cli.commons.CliBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import groovy.transform.Field

@Field
Logger logger = LoggerFactory.getLogger("scriptLogger")

def cli = new CliBuilder(
	usage: 'Verify cooperation import [options]',
	header: '\nAvailable options (use -h for help):\n')

cli.with
	{
		h longOpt: 'help', 'Usage Information', required: false
		d longOpt: 'dumps', 'Comma-separated list of dumps eg. ntjp_qa,ntjp_prod', args: 1, required: true
		url longOpt: 'coop_url', 'Cooperation url', args: 1, required: true
		auth longOpt: 'authentication', 'Username and Password separated by a colon : character', args: 1, required: true
		out longOpt: 'out_file', 'File where results are written', args: 1, required: true
	}

def opt = cli.parse(args)
if (!opt) return
if (opt.h) cli.usage()
def dumps = opt.d.split(",")
def connection_points_url = opt.url
def out_file = opt.out
String auth_userandpass = opt.auth

logger.info("Verify cooperation import")
def error_data = []
def error = ""

logger.info("Preparing Request and Auth.")

// Encode Auth string.
def authString = new String(Base64.getEncoder().encode(auth_userandpass.getBytes()))

// Open connection and attach header.
def conn = connection_points_url.toURL().openConnection()
conn.setRequestProperty( "Authorization", "Basic ${authString}" )

if( conn.responseCode != 200 ) {
	error = "Something unexpected happened while establishing connection to the Connection Points endpoint: ${conn.responseCode}: ${conn.responseMessage}"
	logger.error(error)
	error_data << error
}
def connectionPoints = new JsonSlurper().parseText(conn.content.text)

dumps.each{dump_name ->
	Dump current_dump = Dump.getDump(dump_name)
	if(current_dump == null) {
		error = "Dump med namn [" + dump_name + "] har fel syntax. Kontrollera konfigurationen."
		logger.error(error)
		error_data << error
		return
	}

	def connectionPoint = connectionPoints.find{dump -> dump.platform==current_dump.platform && dump.environment==current_dump.environment}

	if(connectionPoint == null){
			error = "Dump med parametrar: [" + current_dump + "] finns inte i cooperation"
			logger.error(error)
			error_data << error
			return
	}

	if(!isToday(connectionPoint.snapshotTime)){
			error = "Dump [" + connectionPoint + "] ar gammal"
			logger.error(error)
			error_data << error
			return
	}
	logger.info(dump_name + " = " + connectionPoint)
}

new File(out_file).withWriter { writer ->
	error_data.each { line ->
		writer.writeLine line
	}
}

if(!error_data.isEmpty()){
	logger.error("Cooperation import fail. ")
	System.exit(1)
}else{
	logger.info("Cooperation import lyckades.")
}

static boolean isToday(String dump_date_string){
	def dump_date_day = dump_date_string.substring(0, 10)
	def today = new Date().format("YYYY-MM-dd")
	return today.equals(dump_date_day)
}

class Dump {
	Dump(platform, environment) {
		this.platform = platform
		this.environment = environment
	}

	static Dump getDump(String name){
		try {
			def (platform, environment) = name.tokenize('_')
			return new Dump(platform.toUpperCase(), environment.toUpperCase())
		}
		catch (Exception e) {
			return null
		}
	}

	String platform
	String environment
}
