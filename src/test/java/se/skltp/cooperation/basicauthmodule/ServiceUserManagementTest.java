package se.skltp.cooperation.basicauthmodule;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

  private static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

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
  void whenCreatingDummmyUsers_AddAndRetrieveUsers_usersAreAsExpected() {
    ServiceUserListWrapper dummies = mgmt.getDummyUserList();
    assertEquals(dummies.getUsers().size(), 3);
  }

  @Test
  void whenSerializingAndDeserializingUser_GsonPluginWorksAsExpected() {
    ServiceUser user = createQuickDummyUser1_Caesar();
    String userSerialized = gson.toJson(user); // Serialize.
    ServiceUser userDeserialized = gson.fromJson(userSerialized, ServiceUser.class); // Deserialize.

    assertEquals(user, userDeserialized);
  }

  public static ServiceUser createQuickDummyUser1_Caesar() {
    ServiceUser user = new ServiceUser(
        "Caesar",
        MyUserDetailsService.generateHashedPassword("Qwert123"),
        // For specimen password "qwerty"...:
        // Stored as BCrypt-encode at strength 10 as "$2y$10$Ffs4rDCIok.I3uuQ8IIMxufD5FoTvhxymukqEBElHwRxEvaLy8dRO",
        // Sent over web, encoded as BASE64 it is: "SGVucmlrOnF3ZXJ0eQ=="
        "Caesar Julius",
        "NMT",
        "cc@a.aa",
        "073-1234567",
        Arrays.asList(Settings.REG_USER_ROLE, Settings.REG_ADMIN_ROLE)
    );
    return user;
  }
}
