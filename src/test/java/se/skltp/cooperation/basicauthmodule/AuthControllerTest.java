package se.skltp.cooperation.basicauthmodule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import se.skltp.cooperation.basicauthmodule.model.*;
import se.skltp.cooperation.basicauthmodule.model.dto.PasswordChange;
import se.skltp.cooperation.basicauthmodule.model.dto.PasswordCrypto;
import se.skltp.cooperation.basicauthmodule.model.dto.UserData;

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
		settings.apiAllowDownloadSampleUserList = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.retrieveDummyUsers());
		settings.apiAllowDownloadSampleUserList = true;
		ServiceUserListWrapper wrapper = ctrl.retrieveDummyUsers();
		assertEquals(wrapper.getUsers().size(),3);
		assertEquals(wrapper.getUsers().get(1).username,"Anders");
	}

	@Test
	void whengetHash_looksLikeAHash() {
		PasswordCrypto payload = new PasswordCrypto("Qwert123");
		settings.apiAllowGenerateCryptHash = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.getHash(payload));
		settings.apiAllowGenerateCryptHash = true;
		String response = ctrl.getHash(payload);
		assertTrue(response.contains("$2a$10$"));
	}


	@Test
	void whenCreateEditAndChangePasswordOnUser_flowWorks() {
		UserData userDataUser = new UserData(
			"Eskil",
			"Qwerty123",
			"Eskil Usersson",
			"NMT",
			"uu@a.aa",
			"073-2468013",
			Collections.singletonList(Settings.REG_USER_ROLE)
		);
		UserData userDataAdmin = new UserData(
			"Federico",
			"Qwerty123",
			"Federico Adminsson",
			"NMT",
			"uu@a.aa",
			"073-2468013",
			Arrays.asList(Settings.REG_USER_ROLE, Settings.REG_ADMIN_ROLE, Settings.AUTH_ADMIN_ROLE)
		);

		//// ADD User
		// TEST globally locked user addition or editing for ADD/EDIT Users.
		settings.apiAllowAnyUserManagementChanges = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.createOrEditUser(userDataUser));
		settings.apiAllowAnyUserManagementChanges = true;
		// TEST Create User Lock.
		settings.apiAllowCreateAnyUsers = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.createOrEditUser(userDataUser));
		settings.apiAllowCreateAnyUsers = true;
		// adding
		ServiceUser user1 = ctrl.createOrEditUser(userDataUser);

		//// EDIT User
		userDataUser.contactPhone = "076-9876543";
		// Test non-null pwd failure
		assertThrows(ResponseStatusException.class, () -> ctrl.createOrEditUser(userDataUser));
		userDataUser.password = null;
		// TEST EDIT user lock.
		settings.apiAllowEditExistingUsers = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.createOrEditUser(userDataUser));
		settings.apiAllowEditExistingUsers = true;
		// editing
		ServiceUser user2 = ctrl.createOrEditUser(userDataUser);

		assertEquals(user1.username, user2.username);
		assertNotEquals(user1.contactPhone, user2.contactPhone);

		//// CREATE new admin.
		// TEST Create Admin Lock.
		settings.apiAllowCreateSuperAdmins = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.createOrEditUser(userDataAdmin));
		settings.apiAllowCreateSuperAdmins = true;
		// creation.
		ctrl.createOrEditUser(userDataAdmin);

		//// EDIT Admin
		userDataAdmin.contactPhone = "076-9876543";
		userDataAdmin.password = null;
		// TEST Edit Admin Lock.
		settings.apiAllowEditSuperAdmins = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.createOrEditUser(userDataAdmin));
		settings.apiAllowEditSuperAdmins = true;
		// editing.
		ctrl.createOrEditUser(userDataAdmin);

		//// TESTING get-users on currently added users.
		// TEST USER GET LOCK.
		settings.apiAllowGetUsers = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.getUsersCleaned());
		settings.apiAllowGetUsers = true;

		ServiceUserListWrapper usersCleaned = ctrl.getUsersCleaned();
		assertEquals(3, usersCleaned.getUsers().size());
		assertEquals(usersCleaned.getUsers().get(1).username, user1.username);


		//// TESTING PASSWORD CHANGES
		// Dummy datas for pwd change test.
		PasswordChange pwdChangeUserBadPwd = new PasswordChange(
			"Eskil",
			"qwerty"
		);
		PasswordChange pwdChangeUserGoodPwd = new PasswordChange(
			"Eskil",
			"Azerty321"
		);
		PasswordChange pwdChangeAdmin = new PasswordChange(
			"Caesar",
			"Azerty321"
		);

		// TEST globally locked user addition or editing for EDIT User Password.
		settings.apiAllowAnyUserManagementChanges = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.updatePassword(pwdChangeUserGoodPwd));
		settings.apiAllowAnyUserManagementChanges = true;
		// TEST All pwd change lock.
		settings.apiAllowChangePassword = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.updatePassword(pwdChangeUserGoodPwd));
		settings.apiAllowChangePassword = true;
		// TEST ADMIN pwd change lock.
		settings.apiAllowChangeSuperAdminPassword = false;
		assertThrows(ResponseStatusException.class, () -> ctrl.updatePassword(pwdChangeAdmin));
		settings.apiAllowChangeSuperAdminPassword = true;
		// TEST BAD User pwd.
		assertThrows(ResponseStatusException.class, () -> ctrl.updatePassword(pwdChangeUserBadPwd));
		// Change User pwd
		ServiceUser user3 = ctrl.updatePassword(pwdChangeUserGoodPwd);
		assertEquals(user2.username, user3.username);
	}
}
