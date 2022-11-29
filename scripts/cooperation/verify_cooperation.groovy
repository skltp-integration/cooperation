#!/usr/bin/env groovy

@Grapes([
        @Grab(group = 'com.sun.mail', module = 'javax.mail', version = '1.6.1'),
		@Grab(group='commons-lang', module='commons-lang', version='2.6'),
		    @Grab(group = 'ch.qos.logback', module = 'logback-classic', version = '1.2.3'),
		@Grab(group = 'net.logstash.logback', module = 'logstash-logback-encoder', version='6.4'),
        @GrabConfig(systemClassLoader = true)
])


import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import groovy.transform.Field
import org.apache.commons.lang.exception.ExceptionUtils
import groovy.json.JsonSlurper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import groovy.transform.Field

@Field
static Logger logger = LoggerFactory.getLogger("scriptLogger")

def cli = new CliBuilder(
	usage: 'Verify cooperation import [options]',
	header: '\nAvailable options (use -h for help):\n')

cli.with
	{
		h longOpt: 'help', 'Usage Information', required: false
		dumps longOpt: 'dumps', 'Dumps list', args: '1', required: true
		mail longOpt: 'to_mail', 'Alert mail adress', args: 1, required: true
		from_mail longOpt: 'from_mail', 'Alert mail sender', args: 1, required: true
		subj longOpt: 'subjekt', 'Mail subjekt', args: 1, required: true
		coop longOpt: 'coop', 'Cooperation url', args: 1, required: true
		smtp_prop longOpt: 'smtp_prop', 'File with smtp server properties', args: 1, required: true
		ok_file longOpt: 'ok_file', 'Signal fil för visa att verifikation lyckades', args: 1, required: true
		userandpass longOpt: 'user_and_pass', 'Username and Password separated by a colon : character', args: 1, required: true
	}

def opt = cli.parse(args)
if (!opt) return
if (opt.h) cli.usage()

def to_mail = opt.mail
def from_mail = opt.from_mail
def subjekt = opt.subj ? opt.subj : ''
def dump_list = opt.dumps.split("\n")
def connection_points_url = opt.coop
def smtp_prop_file = opt.smtp_prop
def ok_file = opt.ok_file
String auth_userandpass = opt.userandpass

Properties appProperties = downloadProperties(smtp_prop_file)

logger.info("Preparing Request and Auth.")

// Old solution, direct call.
//   String connectionPointsJson = connection_points_url.toURL().text

// Encode Auth string.
def authString = new String(Base64.getEncoder().encode(auth_userandpass.getBytes()))

// Open connection and attach header.
def conn = connection_points_url.toURL().openConnection()
conn.setRequestProperty( "Authorization", "Basic ${authString}" )

if( conn.responseCode != 200 ) {
	println "Something unexpected happened while establishing connection to the Connection Points endpoint."
	println "${conn.responseCode}: ${conn.responseMessage}"
}

def connectionPoints = new JsonSlurper().parseText(conn.content.text)

// Old JSON Slurper
//   def connectionPoints = new JsonSlurper().parseText(connectionPointsJson)

logger.info("Verify cooperation import")

def error_data = []
dump_list.each{dump_name ->
	Dump current_dump = Dump.getDump(dump_name)
	if(current_dump == null) {
		error = "Finns inte beskrivning av en dump med namn [" + dump_name + "] i ett skript! Fix verify_cooperation.groovy skriptet"
		logger.error(error)
		error_data << error
		return
	}

	connectionPoint = connectionPoints.find{dump -> dump.platform==current_dump.platform && dump.environment==current_dump.environment}

	if(connectionPoint == null){
			error = "Dump med parametrar: [" + current_dump + "] finns inte i cooperation"
			logger.error(error)
			error_data << error
			return
	}

	if(!isToday(connectionPoint.snapshotTime)){
			error = "Dump [" + connectionPoint + "] ar gamal"
			logger.error(error)
			error_data << error
			return
	}
	logger.info(dump_name + " = " + connectionPoint)
}

if(!error_data.isEmpty()){
	logger.error("Cooperation import fail. ")
	sendProblemMail(appProperties, to_mail, from_mail, subjekt, error_data)
}else{
	logger.info("Cooperation import lyckades.")
}

def okFile = new File(ok_file)
okFile.write("OK")


boolean isToday(String dump_date_string){
	dump_date_day = Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", dump_date_string).clearTime()
	def today = new Date().clearTime()
	return today.equals(dump_date_day)
}

enum Dump {
	NTJP_TEST("NTJP","TEST"),
	NTJP_QA("NTJP","QA"),
	NTJP_PROD("NTJP","PROD"),
	SLL_QA("SLL","QA"),
	SLL_PROD("SLL","PROD"),
	LD_PROD("LD","PROD"),
	LD_QA("LD","QA"),
	NMT_SKAULO("NMT","SKAULO")


	static Dump getDump(String name){
	name = name.toLowerCase()
    switch (name) {
		case "ntjp_test": return NTJP_TEST;
		case "ntjp_qa": return NTJP_QA;
		case "ntjp_prod": return NTJP_PROD;
		case "sll_qa": return SLL_QA;
		case "sll_prod": return SLL_PROD;
		case "ld_prod": return LD_PROD;
		case "ld_qa": return LD_QA;
		case "nmt_skaulo": return NMT_SKAULO;
		default:  return null;
		}
	}

	Dump(platform, environment){
		this.platform = platform
		this.environment = environment
	}

	String platform
	String environment
}


private static void sendProblemMail(Properties smtpProperties, String to_mail, String from_mail, String subject, ArrayList errors) {
    try {
        Session session = Session.getInstance(smtpProperties)

        MimeMessage msg = new MimeMessage(session)

		String errorText = "Cooperation import fail. Under verifikation inträffades problem: \n"

        msg.setText(errorText + errors);
        msg.setSubject(subject)
        msg.setFrom(new InternetAddress(from_mail))

		String [] mailAddress = to_mail.split("\\s*,\\s*")
		for(String currentAddress:mailAddress){
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(currentAddress.trim()))
		}

        Transport.send(msg);

        logger.info("Mail skickad till support " + to_mail)
    } catch (Exception ex) {
        logger.error((ExceptionUtils.getStackTrace(ex)))
    }
}


private static Properties downloadProperties(String propertiesFileName) {
    File propertiesFile = new File(propertiesFileName)
    Properties properties = new Properties()
    propertiesFile.withInputStream {
        properties.load(it)
    }
    properties
}

