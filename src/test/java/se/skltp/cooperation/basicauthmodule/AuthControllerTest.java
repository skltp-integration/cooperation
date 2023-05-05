package se.skltp.cooperation.basicauthmodule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.skltp.cooperation.basicauthmodule.model.*;

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

	}

	@Test
	void whengetHash_looksLikeAHash() {
		DTO_PasswordCrypto payload = new DTO_PasswordCrypto("Qwert123");
		String response = ctrl.getHash(payload);
		assertTrue(response.contains("$2a$10$"));
	}

	@Test
	void getUsersCleaned() {
	}

	@Test
	void whenCreateEditAndChangePasswordOnUser_flowWorks() {
		DTO_UserData userdata = new DTO_UserData(
			"Uffe",
			"Qwerty123",
			"Uffe Usersson",
			"NMT",
			"uu@a.aa",
			"073-2468013",
			Collections.singletonList("USER")
		);

		settings.allowApi_editExistingUsers = true;

		ServiceUser user1 = ctrl.createOrEditUser(userdata);
		userdata.contactPhone = "076-9876543";
		userdata.password = null;
		ServiceUser user2 = ctrl.createOrEditUser(userdata);

		assertEquals(user1.username, user2.username);
		assertNotEquals(user1.contactPhone, user2.contactPhone);

		DTO_PasswordChange pwdChange = new DTO_PasswordChange(
			"Uffe",
			"Azerty321"
		);
		ServiceUser user3 = ctrl.updatePassword(pwdChange);
		assertEquals(user2.username, user3.username);

		ServiceUserListWrapper usersCleaned = ctrl.getUsersCleaned();
		assertEquals(usersCleaned.users.size(), 2);
		assertEquals(usersCleaned.users.get(1).username, user1.username);
	}

	@Test
	void updatePassword() {
	}
}
