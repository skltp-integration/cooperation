package se.skltp.cooperation.basicauthmodule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import se.skltp.cooperation.basicauthmodule.model.*;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AuthControllerTest {

	@Autowired
	AuthController ctrl;

	@Autowired
	Settings settings;

	@Test
	void whenretrieveDummyUsers() {
		settings.allowApi_downloadSampleUserList = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.retrieveDummyUsers());
		settings.allowApi_downloadSampleUserList = true;
		ServiceUserListWrapper wrapper = ctrl.retrieveDummyUsers();
		assertEquals(wrapper.users.size(),3);
		assertEquals(wrapper.users.get(1).username,"Anders");
	}

	@Test
	void whengetHash_looksLikeAHash() {
		DTO_PasswordCrypto payload = new DTO_PasswordCrypto("Qwert123");
		settings.allowApi_generateCryptHash = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.getHash(payload));
		settings.allowApi_generateCryptHash = true;
		String response = ctrl.getHash(payload);
		assertTrue(response.contains("$2a$10$"));
	}


	@Test
	void whenCreateEditAndChangePasswordOnUser_flowWorks() {
		DTO_UserData userDataUser = new DTO_UserData(
			"Eskil",
			"Qwerty123",
			"Eskil Usersson",
			"NMT",
			"uu@a.aa",
			"073-2468013",
			Collections.singletonList("USER")
		);
		DTO_UserData userDataAdmin = new DTO_UserData(
			"Federico",
			"Qwerty123",
			"Federico Adminsson",
			"NMT",
			"uu@a.aa",
			"073-2468013",
			Arrays.asList("USER", "ADMIN", Settings.authAdminRoleLabel)
		);

		//// ADD User
		// TEST globally locked user addition or editing for ADD/EDIT Users.
		settings.allowApi_anyUserManagementChanges = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.createOrEditUser(userDataUser));
		settings.allowApi_anyUserManagementChanges = true;
		// TEST Create User Lock.
		settings.allowApi_createAnyUsers = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.createOrEditUser(userDataUser));
		settings.allowApi_createAnyUsers = true;
		// adding
		ServiceUser user1 = ctrl.createOrEditUser(userDataUser);

		//// EDIT User
		userDataUser.contactPhone = "076-9876543";
		// Test non-null pwd failure
		assertThrows(ResponseStatusException.class, () -> ctrl.createOrEditUser(userDataUser));
		userDataUser.password = null;
		// TEST EDIT user lock.
		settings.allowApi_editExistingUsers = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.createOrEditUser(userDataUser));
		settings.allowApi_editExistingUsers = true;
		// editing
		ServiceUser user2 = ctrl.createOrEditUser(userDataUser);

		assertEquals(user1.username, user2.username);
		assertNotEquals(user1.contactPhone, user2.contactPhone);

		//// CREATE new admin.
		// TEST Create Admin Lock.
		settings.allowApi_createSuperAdmins = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.createOrEditUser(userDataAdmin));
		settings.allowApi_createSuperAdmins = true;
		// creation.
		ctrl.createOrEditUser(userDataAdmin);

		//// EDIT Admin
		userDataAdmin.contactPhone = "076-9876543";
		userDataAdmin.password = null;
		// TEST Edit Admin Lock.
		settings.allowApi_editSuperAdmins = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.createOrEditUser(userDataAdmin));
		settings.allowApi_editSuperAdmins = true;
		// editing.
		ctrl.createOrEditUser(userDataAdmin);

		//// TESTING get-users on currently added users.
		// TEST USER GET LOCK.
		settings.allowApi_getUsers = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.getUsersCleaned());
		settings.allowApi_getUsers = true;

		ServiceUserListWrapper usersCleaned = ctrl.getUsersCleaned();
		assertEquals(3, usersCleaned.users.size());
		assertEquals(usersCleaned.users.get(1).username, user1.username);


		//// TESTING PASSWORD CHANGES
		// Dummy datas for pwd change test.
		DTO_PasswordChange pwdChangeUserBadPwd = new DTO_PasswordChange(
			"Eskil",
			"qwerty"
		);
		DTO_PasswordChange pwdChangeUserGoodPwd = new DTO_PasswordChange(
			"Eskil",
			"Azerty321"
		);
		DTO_PasswordChange pwdChangeAdmin = new DTO_PasswordChange(
			"Caesar",
			"Azerty321"
		);

		// TEST globally locked user addition or editing for EDIT User Password.
		settings.allowApi_anyUserManagementChanges = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.updatePassword(pwdChangeUserGoodPwd));
		settings.allowApi_anyUserManagementChanges = true;
		// TEST All pwd change lock.
		settings.allowApi_changePassword = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.updatePassword(pwdChangeUserGoodPwd));
		settings.allowApi_changePassword = true;
		// TEST ADMIN pwd change lock.
		settings.allowApi_changeSuperAdminPassword = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.updatePassword(pwdChangeAdmin));
		settings.allowApi_changeSuperAdminPassword = true;
		// TEST BAD User pwd.
		assertThrows(ResponseStatusException.class, () -> ctrl.updatePassword(pwdChangeUserBadPwd));
		// Change User pwd
		ServiceUser user3 = ctrl.updatePassword(pwdChangeUserGoodPwd);
		assertEquals(user2.username, user3.username);
	}
}
