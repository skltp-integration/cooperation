/**
 * Transform files in directory from the TAK export format into the format
 * expected by the Cooperation import-to-database script.
 */

import groovy.io.FileType
import groovy.json.*
import java.text.SimpleDateFormat


def transformFile(File infile) {
    def jsonSlurper = new JsonSlurper();
    def inJsonRoot = jsonSlurper.parseText(infile.text)
    def outJsonRoot = jsonSlurper.parseText(infile.text)

    // rename original file, trust later steps to only filter out files with ".json" suffix
    def originalInFilename = infile.getPath()
    if (!infile.renameTo(originalInFilename + ".original.before.transform")) {
        throw new IOException("Could not rename file: " + originalInFilename)
    }
	// only files from new format needs transformation
    if (inJsonRoot.utforare && inJsonRoot.utforare.equalsIgnoreCase("TakExport script")) {
		transformJson(inJsonRoot, outJsonRoot)
	}
    new File(originalInFilename).write(JsonOutput.prettyPrint(JsonOutput.toJson(outJsonRoot)))
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

    long nowMs = System.currentTimeMillis()
    // Example date: 2014-08-12T22:00:00+0000
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    Iterator i = outJsonRoot.data.anropsbehorighet.iterator()
    while(i.hasNext()) {
        it = i.next()
        Date dFrom = dateFormat.parse(it.fromTidpunkt)
        Date dTom = dateFormat.parse(it.tomTidpunkt)
        if (dFrom.getTime() <= nowMs && dTom.getTime() >= nowMs) {
            it.relationships.logiskAdress = idMapLogiskAdress.get(it.relationships.logiskAdress).hsaId
            it.relationships.tjanstekonsument = idMapTjanstekomponent.get(it.relationships.tjanstekonsument).hsaId
            it.relationships.tjanstekontrakt = idMapTjanstekontrakt.get(it.relationships.tjanstekontrakt).namnrymd
        }
        else {
            i.remove()
        }
    }

    /*
     * vagval: filter on date-interval,
     *  resolve anropsadress, logiskadress, tjanstekontrakt
     *  add relationships for: tjansteproducent, rivtaProfil
     */
    Map idMapAnropsadress = new HashMap()
    inJsonRoot.data.anropsadress.each {
        idMapAnropsadress.put(it.id, it)
    }

    i = outJsonRoot.data.vagval.iterator()
    while(i.hasNext()) {
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
        }
        else {
            i.remove()
        }
    }
}


def cli = new CliBuilder(
    usage: 'TransformTakExportFormatToCooperationImportFormat.groovy [options]',
    header: '\nAvailable options (use -h for help):\n')
cli.with {
    h longOpt: 'help', 'Usage Information', required: false
    d longOpt: 'directory', 'Directory that holds data dump files', args: 1, required: true
}

def opt = cli.parse(args)
if (!opt) return
if (opt.h) cli.usage()

def dataDirectory = opt.d ? opt.d.replaceFirst("^~",System.getProperty("user.home")) : '.'

println "Begin: transform files in dir: " + dataDirectory
def directory = new File(dataDirectory)
directory.eachFileMatch(FileType.FILES, ~/.*json/) {
    println "Begin: transform file: " + it.name
    transformFile(it)
    println "End: transform file: " + it.name
}
println "End: transform files"
