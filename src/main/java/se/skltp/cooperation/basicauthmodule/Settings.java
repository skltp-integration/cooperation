package se.skltp.cooperation.basicauthmodule;

import org.springframework.beans.factory.annotation.Value;

public class Settings {

	// User file settings:

	/** Absolute or relative folder path. Used to check if the folder exists, and create it if not. */
	// TODO: Implement @Value
	static final String folderPath = "./config/";
	// TODO: Implement @Value
	/** Filename with .json file type. */
	static final String fileName = "userlist.json";
	/** Absolute or relative file path. Used for file read and write. Concatenated from other values. */
	static final String filePath = folderPath + fileName;
	/** A CRON temporal expression for how often the user file should be read.<br>
	 * Currently: "At 5 minutes past the hour, every hour, every day." */
	// TODO: Implement @Value("") to not have to build on cron-timing changes.
	// @Value("ewq", default="qwe")
	static final String fileReadCron = "0 5 * * * *";

	// API settings:

	/** Primary sub-uri path for the endpoints within the AuthController. */
	static final String authAdministrationSubPath = "/authoring";

	// On-Off flags for endpoints or types of endpoints.
	static final boolean allowEndpoint_generateCryptHash = false;
	static final boolean allowEndpoint_downloadUsers = false;
	static final boolean allowEndpoint_rescanUserFile = true;
	static final boolean allowEndpoint_resetUserFile = false;
	static final boolean allowEndpoint_downloadSampleUserFile = true;
	static final boolean allowEndpoint_testSerialization = false;
	static final boolean allowEndpoint_hellosAndPings = true;
}
