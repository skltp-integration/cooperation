#!/usr/bin/env groovy

@Grapes([
        @Grab(group = 'com.sun.mail', module = 'javax.mail', version = '1.6.1'),
		@Grab(group='commons-lang', module='commons-lang', version='2.6'),
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

Properties appProperties = downloadProperties(smtp_prop_file)
String connectionPointsJson = connection_points_url.toURL().text

def connectionPoints = new JsonSlurper().parseText(connectionPointsJson)

def errors = []

dump_list.each{dump_name -> 
	Dump current_dump = Dump.getDump(dump_name)
	if(current_dump == null) {
		errors << "Finns inte beskrivning av en dump med namn [" + dump_name + "] i ett skript! Fix skriptet"
		return
	}
	
	connectionPoint = connectionPoints.find{dump -> dump.platform==current_dump.platform && dump.environment==current_dump.environment}

	if(connectionPoint == null){
			errors << "Dump med parametrar: [" + current_dump + "] finns inte i cooperation"
			return
	}
	
	if(!isToday(connectionPoint.snapshotTime)){
			errors << "Dump ["+ connectionPoint+"] ar gamal"
			return
	}
	println dump_name + " = " + connectionPoint
}

if(!errors.isEmpty()){
	println "Cooperation import fail. " + errors
	sendProblemMail(appProperties, to_mail, from_mail, subjekt, errors)
}else{
	println "Cooperation import lyckades."
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
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to_mail))
        msg.setFrom(new InternetAddress(from_mail))

        Transport.send(msg);

        println "Mail skickad till support " + to_mail
    } catch (Exception ex) {
        println(ExceptionUtils.getStackTrace(ex))
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

