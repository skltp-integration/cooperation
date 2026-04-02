package se.skltp.cooperation.basicauthmodule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.skltp.cooperation.basicauthmodule.model.ServiceUser;
import se.skltp.cooperation.basicauthmodule.model.dto.UserData;
import se.skltp.cooperation.basicauthmodule.model.ServiceUserListWrapper;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ServiceUserManagementTest {

  @Autowired
  ServiceUserManagement mgmt;

  @Test
  void whenAddingAndDeletingUser_worksAsExpected() {
    UserData testUser = new UserData(
		"DGA9FEK2NVYPSA7MCRHB3VEREE85KK",
		"qwerty",
		// For specimen password "qwerty"...:
		// Stored as BCrypt-encode at strength 10 as "$2y$10$Ffs4rDCIok.I3uuQ8IIMxufD5FoTvhxymukqEBElHwRxEvaLy8dRO",
		// Sent over web, encoded as BASE64 it is: "SGVucmlrOnF3ZXJ0eQ=="
		"Caesar Julius",
		"NMT",
		"cj@a.aa",
		"073-1234567",
		Arrays.asList(Settings.REG_USER_ROLE, Settings.REG_ADMIN_ROLE, Settings.AUTH_ADMIN_ROLE)
	);

	ServiceUser userSaved = mgmt.createUserFlow(testUser);
    assertTrue(mgmt.userExists(testUser.username));

    ServiceUser userStored = mgmt.findUser(testUser.username);

	userStored.password = Settings.REDACTED_LABEL;
	userSaved.password = Settings.REDACTED_LABEL;

	assertEquals(userSaved, userStored);

    mgmt.deleteUserTest(testUser.username);
    assertFalse(mgmt.userExists(testUser.username));
  }

  @Test
  void whenCreatingDummyUsers_AddAndRetrieveUsers_usersAreAsExpected() {
    ServiceUserListWrapper dummies = mgmt.getDummyUserList();
    assertEquals(3, dummies.getUsers().size());
  }
}
