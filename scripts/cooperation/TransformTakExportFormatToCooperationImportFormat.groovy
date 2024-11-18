/**
 * Transform files in directory from the TAK export format into the format
 * expected by the Cooperation import-to-database script.
 */

@Grapes([
	@GrabConfig(systemClassLoader = true),
	@Grab(group = 'ch.qos.logback',       module = 'logback-classic',          version = '1.2.3'),
	@Grab(group = 'net.logstash.logback', module = 'logstash-logback-encoder', version = '6.4'),
	@Grab(group = 'co.elastic.logging',   module = 'logback-ecs-encoder',      version = '1.5.0'),
])

import groovy.json.*
import groovy.cli.commons.CliBuilder
import groovy.io.FileType
import groovy.transform.Field
import groovy.util.ConfigSlurper
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.text.SimpleDateFormat

@Field
static Logger logger = LoggerFactory.getLogger("scriptLogger")


def cli = new CliBuilder(
	usage: 'TransformTakExportFormatToCooperationImportFormat.groovy [options]',
	header: '\nAvailable options (use -h for help):\n')
cli.with {
	h longOpt: 'help', 'Usage Information', required: false
	d longOpt: 'directory', 'Directory that holds data dump files', args: 1, required: true
}

try {
	def opt = cli.parse(args)
	if (!opt) return
	if (opt.h) cli.usage()

	def dataDirectory = opt.d ? opt.d.replaceFirst("^~", System.getProperty("user.home")) : '.'

	transformation(dataDirectory)

} catch (Exception e) {
	logger.error("Exception in TransformTakExportFormatToCooperationImportFormat.groovy", e)
	throw e
}


def transformation(dataDirectory) {
// Read configuration
	final ConfigObject config = new ConfigSlurper().parse(new File("CoopConfig.groovy").toURI().toURL());
	logger.info("Begin: transform files in dir: " + dataDirectory)
	def directory = new File(dataDirectory)

	config.environments.each {
		envs ->
			logger.info("Environment ${envs}")
			directory.eachFileMatch(FileType.FILES, ~".*${envs}\\.json") { it ->
				logger.info("Begin: transform file: " + it.name)
				transformFile(it)
				logger.info("End: transform file: " + it.name)
			}
	}
	logger.info("End: transform files")
}


def transformFile(File infile) {
	try {
		def jsonSlurper = new JsonSlurper();
		def inJsonRoot = jsonSlurper.parseText(infile.text)
		def outJsonRoot = jsonSlurper.parseText(infile.text)

		// rename original file, trust later steps to only filter out files with ".json" suffix
		def originalInFilename = infile.getPath()
		if (!infile.renameTo(originalInFilename + ".original.before.transform")) {
			throw new IOException("Could not rename file: " + originalInFilename)
		}
		/* only files from new format may need transformation
           the difference is that some files use id rather than hsaId
           and in som case tjanstekomponent instead of tjanstekonsument
           in relationships.
           We need to make more formal versions.
        */
		if (inJsonRoot.utforare && inJsonRoot.utforare.equalsIgnoreCase("TakExport script")) {
			transformJson(inJsonRoot, outJsonRoot)
		} else {
			transformJsonOnlyDateFiltrering(outJsonRoot);
		}
		new File(originalInFilename).write(JsonOutput.prettyPrint(JsonOutput.toJson(outJsonRoot)))
	} catch (Exception e) {
		logger.error("Exception: transformFile: " + infile.name, e)
	}
}

/**
 * The two JSON-structures must be identical to start with.
 * Do not mutate inJsonRoot, keep as is for reference and lookups during
 * transformation to outJsonRoot.
 */
def transformJson(Map inJsonRoot, Map outJsonRoot) {

	/*
     * tjanstekomponent: split into: tjanstekonsument, tjansteproducent
     */
	outJsonRoot.data.remove("tjanstekomponent")

	Set anropsbehorighetIds = new HashSet()
	inJsonRoot.data.anropsbehorighet.each {
		anropsbehorighetIds.add(it.relationships.tjanstekonsument)
	}

	Set anropsadressIds = new HashSet()
	inJsonRoot.data.anropsadress.each {
		anropsadressIds.add(it.relationships.tjanstekomponent)
	}

	List tjanstekonsumentList = new ArrayList()
	List tjansteproducentList = new ArrayList()
	outJsonRoot.data.put("tjanstekonsument", tjanstekonsumentList)
	outJsonRoot.data.put("tjansteproducent", tjansteproducentList)

	inJsonRoot.data.tjanstekomponent.each {
		if (anropsbehorighetIds.contains(it.id)) {
			tjanstekonsumentList.add(it)
		}
		if (anropsadressIds.contains(it.id)) {
			tjansteproducentList.add(it)
		}
	}

	/*
     * anropsadress: resolve: rivtaprofil, tjanstekomponent
     */
	Map idMapRivtaprofil = new HashMap()

	inJsonRoot.data.rivtaprofil.each {
		idMapRivtaprofil.put(it.id, it)
	}

	Map idMapTjanstekomponent = new HashMap()
	inJsonRoot.data.tjanstekomponent.each {
		idMapTjanstekomponent.put(it.id, it)
	}

	outJsonRoot.data.anropsadress.each {
		it.relationships.rivtaprofil = idMapRivtaprofil.get(it.relationships.rivtaprofil).namn
		it.relationships.tjanstekomponent = idMapTjanstekomponent.get(it.relationships.tjanstekomponent).hsaId
	}

	/*
     * anropsbehorighet: filter on date-interval, resolve logiskAdress, tjanstekonsument, tjanstekontrakt
     */
	Map idMapLogiskAdress = new HashMap()
	inJsonRoot.data.logiskadress.each {
		idMapLogiskAdress.put(it.id, it)
	}

	Map idMapTjanstekontrakt = new HashMap()
	inJsonRoot.data.tjanstekontrakt.each {
		idMapTjanstekontrakt.put(it.id, it)
	}

	long nr = 0
	long nowMs = System.currentTimeMillis()
	// Example date: 2014-08-12T22:00:00+0000
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
	Iterator i = outJsonRoot.data.anropsbehorighet.iterator()
	while (i.hasNext()) {
		it = i.next()
		Date dFrom = dateFormat.parse(it.fromTidpunkt)
		Date dTom = dateFormat.parse(it.tomTidpunkt)
		if (dFrom.getTime() <= nowMs && dTom.getTime() >= nowMs) {
			it.relationships.logiskAdress = idMapLogiskAdress.get(it.relationships.logiskAdress).hsaId

			def tjkId = it.relationships.tjanstekonsument == null ? it.relationships.tjanstekomponent : it.relationships.tjanstekonsument

			//remove if exists
			it.relationships.remove("tjanstekomponent");

			it.relationships.tjanstekonsument = idMapTjanstekomponent.get(tjkId).hsaId
			it.relationships.tjanstekontrakt = idMapTjanstekontrakt.get(it.relationships.tjanstekontrakt).namnrymd
		} else {
			nr++
			i.remove()
			logger.info("Removed anropsbehörighet " + it.id + " valid between " + it.fromTidpunkt + " and " + it.tomTidpunkt)
		}
	}
	logger.info("Removed " + nr + " anropsbehörigheter")

	/*
     * vagval: filter on date-interval,
     *  resolve anropsadress, logiskadress, tjanstekontrakt
     *  add relationships for: tjansteproducent, rivtaProfil
     */
	Map idMapAnropsadress = new HashMap()
	inJsonRoot.data.anropsadress.each {
		idMapAnropsadress.put(it.id, it)
	}

	nr = 0
	i = outJsonRoot.data.vagval.iterator()
	while (i.hasNext()) {
		it = i.next()
		Date dFrom = dateFormat.parse(it.fromTidpunkt)
		Date dTom = dateFormat.parse(it.tomTidpunkt)
		if (dFrom.getTime() <= nowMs && dTom.getTime() >= nowMs) {
			Map anropsAdress = idMapAnropsadress.get(it.relationships.anropsadress)

			it.relationships.anropsadress = anropsAdress.adress
			it.relationships.logiskadress = idMapLogiskAdress.get(it.relationships.logiskadress).hsaId
			it.relationships.tjanstekontrakt = idMapTjanstekontrakt.get(it.relationships.tjanstekontrakt).namnrymd
			// dig one more level to add extra relationships
			it.relationships.tjansteproducent = idMapTjanstekomponent.get(anropsAdress.relationships.tjanstekomponent).hsaId
			it.relationships.rivtaProfil = idMapRivtaprofil.get(anropsAdress.relationships.rivtaprofil).namn
		} else {
			nr++
			i.remove()
			logger.info("Removed vägval " + it.id + " valid between " + it.fromTidpunkt + " and " + it.tomTidpunkt)
		}
	}
	logger.info("Removed " + nr + " vägval")
}


def transformJsonOnlyDateFiltrering(Map outJsonRoot) {
	long nr = 0
	long nowMs = System.currentTimeMillis()
	// Example date: 2014-08-12T22:00:00+0000
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
	Iterator i = outJsonRoot.data.anropsbehorighet.iterator()
	while (i.hasNext()) {
		it = i.next()
		Date dFrom = dateFormat.parse(it.fromTidpunkt)
		Date dTom = dateFormat.parse(it.tomTidpunkt)
		if (nowMs < dFrom.getTime() || nowMs > dTom.getTime()) {
			nr++
			i.remove()
			logger.info("removed anropsbehörighet " + it.id + " valid between " + it.fromTidpunkt + " and " + it.tomTidpunkt)
		}
	}
	logger.info("Removed " + nr + " anropsbehörigheter")

	nr = 0
	i = outJsonRoot.data.vagval.iterator()
	while (i.hasNext()) {
		it = i.next()
		Date dFrom = dateFormat.parse(it.fromTidpunkt)
		Date dTom = dateFormat.parse(it.tomTidpunkt)
		if (nowMs < dFrom.getTime() || nowMs > dTom.getTime()) {
			nr++
			logger.info("Removed vägval " + it.id + " valid between " + it.fromTidpunkt + " and " + it.tomTidpunkt)
			i.remove()
		}
	}
	logger.info("Removed " + nr + " vägval")
}


