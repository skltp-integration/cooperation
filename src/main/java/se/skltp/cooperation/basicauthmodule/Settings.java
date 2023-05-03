package se.skltp.cooperation.basicauthmodule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Settings {

	// API settings:

	// On-Off flags for endpoints or types of endpoints.
	@Value("${settings.allowApi_generateCryptHash}") public boolean allowApi_generateCryptHash;
	@Value("${settings.allowApi_downloadUsers}") public boolean allowApi_getUsers;
	@Value("${settings.allowApi_downloadUsersRaw}") public boolean allowApi_getUsersRaw;
	@Value("${settings.allowApi_anyUserManagementChanges}") public boolean allowApi_anyUserManagementChanges;
	@Value("${settings.allowApi_changeUserPassword}") public boolean allowApi_changePassword;
	@Value("${settings.allowApi_changeSuperAdminPassword}") public boolean allowApi_changeSuperAdminPassword;
	@Value("${settings.allowApi_editUsers}") public boolean allowApi_editExistingUsers;
	@Value("${settings.allowApi_editSuperAdmins}") public boolean allowApi_editSuperAdmins;
	@Value("${settings.allowApi_createAnyUsers}") public boolean allowApi_createAnyUsers;
	@Value("${settings.allowApi_createSuperAdmins}") public boolean allowApi_createSuperAdmins;
	@Value("${settings.allowApi_downloadSampleUserFile}") public boolean allowApi_downloadSampleUserList;
	@Value("${settings.allowApi_hellosAndPings}") public boolean allowApi_hellosAndPings;
}
