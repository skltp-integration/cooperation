//////
// 2022-05-17, Henrik Augustsson.
// Nordic Medtest.
//////

package se.skltp.cooperation.basicauthmodule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.skltp.cooperation.basicauthmodule.model.*;

@RestController
@RequestMapping("/authoring")
public final class AuthController {

	@Autowired
	ServiceUserManagement userService;

	@Autowired
	Settings settings;

	////
	// Dummy and Sample Users.
	////

	@GetMapping("/admin/get_user_template")
	public ServiceUserListWrapper retrieveDummyUsers() {
		if (!settings.allowApi_downloadSampleUserList) {
			throw new ResponseStatusException(
				HttpStatus.LOCKED, "Not Available."
			);
		}

		return userService.getDummyUserList();
	}


	////
	// ADMINISTRATIVE
	////

	@PostMapping("/admin/generate_crypto")
	public String getHash(@RequestBody DTO_PasswordCrypto payload) {
		if (!settings.allowApi_generateCryptHash) {
			throw new ResponseStatusException(
				HttpStatus.LOCKED, "String hashing and encryption function is locked."
			);
		}

		return "Provided string \"" + payload.rawPassword + "\" has been 'encrypted' as:\n" +
			MyUserDetailsService.generateHashedPassword(payload.rawPassword);
	}

	@GetMapping("/admin/get_users")
	public ServiceUserListWrapper getUsersCleaned() {
		if (!settings.allowApi_getUsers) {
			throw new ResponseStatusException(
				HttpStatus.LOCKED, "Retrieval of user entries is locked."
			);
		}

		return userService.findAllUsersProcessed();
	}

	@PostMapping("/admin/write_user")
	public ServiceUser createOrEditUser(
		@NonNull @RequestBody DTO_UserData userData)
	{
		// Global API User Upload lock check.
		if (!settings.allowApi_anyUserManagementChanges) {
			throw new ResponseStatusException(
				HttpStatus.LOCKED, "This endpoint has been made unavailable. User editing and creation is locked."
			);
		}

		// Check if user pre-exists.
		boolean exists = userService.userExists(userData.username);
		if (exists) {
			return editUser(userData);
		} else {
			return createUser(userData);
		}
	}

	@PostMapping("/admin/set_password")
	public ServiceUser updatePassword(
		@RequestBody DTO_PasswordChange payload) {

		// Global API User Upload lock check.
		if (!settings.allowApi_anyUserManagementChanges) {
			throw new ResponseStatusException(
				HttpStatus.LOCKED, "This endpoint has been made unavailable. User editing and creation is locked."
			);
		}
		if (!settings.allowApi_changePassword) {
			throw new ResponseStatusException(
				HttpStatus.LOCKED, "User password change via API is locked."
			);
		}

		checkPasswordQuality(payload.newPassword); //throws if bad.
		ServiceUser existingUser = userService.findUser(payload.username); //throws if not found.
		if (existingUser.roles.contains(Settings.authAdminRoleLabel) && !settings.allowApi_changeSuperAdminPassword) {
			throw new ResponseStatusException(
				HttpStatus.FORBIDDEN, "Changing super-admin passwords over API disabled in config. Use database instead."
			);
		}

		return userService.changePasswordFlow(existingUser, payload.newPassword);
	}

	private ServiceUser editUser(DTO_UserData incomingUserData) {
		// At the entry of this function, it is already known that the user exists.

		// Global API User Overwrite lock check.
		if (!settings.allowApi_editExistingUsers) {
			throw new ResponseStatusException(
				HttpStatus.FORBIDDEN, "Editing any existing users over API disabled in config. Use database instead."
			);
		}

		// Global API Admin Overwrite lock check.
		ServiceUser existingUser = userService.findUser(incomingUserData.username);
		if (existingUser.roles.contains(Settings.authAdminRoleLabel)) {
			if(!settings.allowApi_editSuperAdmins) {
				throw new ResponseStatusException(
					HttpStatus.FORBIDDEN, "Editing super-admins over API disabled in config. Use database instead."
				);
			}
		} else {
			if (incomingUserData.roles.contains(Settings.authAdminRoleLabel) && !settings.allowApi_createSuperAdmins) {
				throw new ResponseStatusException(
					HttpStatus.FORBIDDEN, "Elevating User to super-admins over API disabled in config. Use database instead."
				);
			}
		}

		if (incomingUserData.password != null) {
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST, "password property must be kept null when editing an existing user.\n" +
				"Password changes are done via a separate endpoint, or in the database."
			);
		}

		return userService.editUserFlow(incomingUserData, existingUser);
	}
	private ServiceUser createUser(DTO_UserData userData) {

		if (!settings.allowApi_createAnyUsers) {
			throw new ResponseStatusException(
				HttpStatus.FORBIDDEN, "Creation of any users over API disabled in config. Use database instead."
			);
		}

		if (userData.roles.contains(Settings.authAdminRoleLabel) && !settings.allowApi_createSuperAdmins) {
			throw new ResponseStatusException(
				HttpStatus.FORBIDDEN, "Creating super-admins over API disabled in config. Use database instead."
			);
		}

		checkPasswordQuality(userData.password);
		return userService.createUserFlow(userData);
	}

	private static void checkPasswordQuality(String newPassword) {
		if (MyUserDetailsService.isBadPassword(newPassword)) {
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST, "Provided password is malformed.\n" +
				"Min 8 chars. Min 1 num. Min 1 UPPERCASE. Min 1 lowercase. No special chars. No whitespace."
			);
		}
	}
}
