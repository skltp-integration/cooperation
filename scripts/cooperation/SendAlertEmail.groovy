#!/usr/bin/env groovy

@Grapes([
        @Grab(group = 'com.sun.mail', module = 'javax.mail', version = '1.6.1'),
		@Grab(group='commons-lang', module='commons-lang', version='2.6'),
	    @Grab(group = 'ch.qos.logback', module = 'logback-classic', version = '1.5.16'),
		@Grab(group = 'net.logstash.logback', module = 'logstash-logback-encoder', version='8.0'),
		@Grab(group = 'co.elastic.logging', module = 'logback-ecs-encoder', version='1.6.0'),
		@GrabConfig(systemClassLoader = true)
])


import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import org.apache.commons.lang.exception.ExceptionUtils
import groovy.cli.commons.CliBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import groovy.transform.Field

@Field
Logger logger = LoggerFactory.getLogger("scriptLogger")

def cli = new CliBuilder(
	usage: 'Send alert email [options]',
	header: '\nAvailable options (use -h for help):\n')

cli.with
	{
		h longOpt: 'help', 'Usage Information', required: false
		to longOpt: 'to_mail', 'Alert mail adress', args: 1, required: true
		from longOpt: 'from_mail', 'Alert mail sender', args: 1, required: true
		subj longOpt: 'subject', 'Mail subjekt', args: 1, required: true
        login longOpt: 'smtp_login', 'smtp server login username', args: 1, required: true
        host longOpt: 'smtp_host', 'smtp server host', args: 1, required: true
        port longOpt: 'smtp_port', 'smtp server port', args: 1, required: true
		file longOpt: 'filename', 'Fil med innehåll till email', args: 1, required: true
	}

def opt = cli.parse(args)
if (!opt) return
if (opt.h) cli.usage()

def to = opt.to
def from = opt.from
def subject = opt.subj
def login = opt.login
def host = opt.host
def port = opt.port
def file = opt.file

Properties smtpProperties = new Properties()
smtpProperties.setProperty("mail.smtp.login", login)
smtpProperties.setProperty("mail.smtp.host", host)
smtpProperties.setProperty("mail.smtp.port", port)

try {
	Session session = Session.getInstance(smtpProperties)
	MimeMessage msg = new MimeMessage(session)

	String fileContents = new File(file).text
	String errorText = "Cooperation import fail. Under verifikation inträffade problem: \n"

	msg.setText(errorText + fileContents)
	msg.setSubject(subject)
	msg.setFrom(new InternetAddress(from))

	String [] mailAddress = to.split("\\s*,\\s*")
	for(String currentAddress:mailAddress){
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(currentAddress.trim()))
	}

	Transport.send(msg);

	logger.info("Mail skickad till support " + to)
} catch (Exception ex) {
	logger.error((ExceptionUtils.getStackTrace(ex)))
}

