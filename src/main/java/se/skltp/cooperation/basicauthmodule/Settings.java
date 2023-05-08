package se.skltp.cooperation.basicauthmodule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Settings {

	// API settings:

	// On-Off flags for endpoints or types of endpoints.
	@Value("${settings.allowApi_generateCryptHash}") public boolean apiAllowGenerateCryptHash;
	@Value("${settings.allowApi_downloadUsers}") public boolean apiAllowGetUsers;
	@Value("${settings.allowApi_anyUserManagementChanges}") public boolean apiAllowAnyUserManagementChanges;
	@Value("${settings.allowApi_changeUserPassword}") public boolean apiAllowChangePassword;
	@Value("${settings.allowApi_changeSuperAdminPassword}") public boolean apiAllowChangeSuperAdminPassword;
	@Value("${settings.allowApi_editUsers}") public boolean apiAllowEditExistingUsers;
	@Value("${settings.allowApi_editSuperAdmins}") public boolean apiAllowEditSuperAdmins;
	@Value("${settings.allowApi_createAnyUsers}") public boolean apiAllowCreateAnyUsers;
	@Value("${settings.allowApi_createSuperAdmins}") public boolean apiAllowCreateSuperAdmins;
	@Value("${settings.allowApi_downloadSampleUserFile}") public boolean apiAllowDownloadSampleUserList;

	public static final String REG_USER_ROLE = "USER";
	public static final String REG_ADMIN_ROLE = "ADMIN";
	public static final String AUTH_ADMIN_ROLE = "SUPER_ADMIN";
	public static final String REDACTED_LABEL = "[redacted]";

}
