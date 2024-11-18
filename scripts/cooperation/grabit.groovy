#!/usr/bin/env groovy

/**
 *  The purpose of this scripts is to grab dependencies before you run the import/export script in a
 *  locked down environment i.e no internet connection.
 *
 *  When running a Groovy script that uses Grab for dependencies Grape is used as the dependency manager.
 *  Grape is using Ivy for dependency management. Grape uses the ~/.groovy/grapes directory for
 *  downloading libraries so if you don't have internet access then you have to populate this directory manually.
 *
 *  To download all dependency jars you can run this script with the following system property
 *  $ groovy -Dgrape.root=./grape_repo grabit.groovy
 *
 *  The directory ./grape_repo contains all dependency jars that you have to copy to the
 *  ~/.groovy/grapes directory on the server.
 *
 *
 */


// NOTE: 2015-10-08: problems getting hsqldb using grape, had to populate local maven repo first using:
// mvn org.apache.maven.plugins:maven-dependency-plugin:get -DartifactId=hsqldb -DgroupId=org.hsqldb -Dversion=2.3.3
@Grapes([
	@GrabConfig(systemClassLoader=true),
	@Grab(group='com.h2database', module='h2', version='1.4.187'),
	@Grab(group='org.hsqldb', module='hsqldb', version='2.3.3'),
	@Grab(group='mysql', module='mysql-connector-java', version='8.0.29'),
	@Grab(group='co.elastic.logging', module='logback-ecs-encoder', version='1.5.0'),
	@Grab(group = 'ch.qos.logback', module = 'logback-classic', version = '1.5.12'),
])

import groovy.sql.Sql

println 'Done grabbing'
