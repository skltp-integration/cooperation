package se.skltp.cooperation.basicauthmodule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Settings {

	// User file settings:

	/** Absolute or relative folder path. Used to check if the folder exists, and create it if not. */
	@Value("${settings.folderPath:./config/}")
	public String folderPath;

	/** Filename with .json file type. */
	@Value("${settings.fileName:userlist.json}")
	public String fileName;

	/** Absolute or relative file path. Used for file read and write. Concatenated from other values. */
	public final String getFilePath() { return folderPath + fileName ;}

	/** A CRON temporal expression for how often the user file should be read.<br>
	 * Currently: "(0 0/5 * * * *) = Every 5 minutes, every hour, every day." */
	@Value("${settings.fileReadCron:0 0/5 * * * *}")
	public String cron;

	// API settings:

	/** Primary sub-uri path for the endpoints within the AuthController. */
	public final static String authAdministrationSubPath = "/authoring";

	// On-Off flags for endpoints or types of endpoints.
	@Value("${settings.allowEndpoint_generateCryptHash:false}") public boolean allowEndpoint_generateCryptHash;
	@Value("${settings.allowEndpoint_downloadUsers:false}") public boolean allowEndpoint_downloadUsers;
	@Value("${settings.allowEndpoint_rescanUserFile:true}") public boolean allowEndpoint_rescanUserFile;
	@Value("${settings.allowEndpoint_resetUserFile:false}") public boolean allowEndpoint_resetUserFile;
	@Value("${settings.allowEndpoint_downloadSampleUserFile:true}") public boolean allowEndpoint_downloadSampleUserFile;
	@Value("${settings.allowEndpoint_testSerialization:false}") public boolean allowEndpoint_testSerialization;
	@Value("${settings.allowEndpoint_hellosAndPings:true}") public boolean allowEndpoint_hellosAndPings;
}
