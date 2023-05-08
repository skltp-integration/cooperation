package se.skltp.cooperation.basicauthmodule;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.skltp.cooperation.basicauthmodule.model.ServiceUser;
import se.skltp.cooperation.basicauthmodule.model.DTO_UserData;
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
    DTO_UserData testUser = new DTO_UserData(
		"DGA9FEK2NVYPSA7MCRHB3VEREE85KK",
		"qwerty",
		// For specimen password "qwerty"...:
		// Stored as BCrypt-encode at strength 10 as "$2y$10$Ffs4rDCIok.I3uuQ8IIMxufD5FoTvhxymukqEBElHwRxEvaLy8dRO",
		// Sent over web, encoded as BASE64 it is: "SGVucmlrOnF3ZXJ0eQ=="
		"Caesar Julius",
		"NMT",
		"cj@a.aa",
		"073-1234567",
		Arrays.asList("USER", "ADMIN", Settings.authAdminRoleLabel)
	);

	ServiceUser userSaved = mgmt.createUserFlow(testUser);
    assertTrue(mgmt.userExists(testUser.username));

    ServiceUser userStored = mgmt.findUser(testUser.username);

	userStored.password = "ignore";
	userSaved.password = "ignore";

	assertEquals(userSaved, userStored);

    mgmt.deleteUserTest(testUser.username);
    assertFalse(mgmt.userExists(testUser.username));
  }

  @Test
  void whenCreatingDummmyUsers_AddAndRetrieveUsers_usersAreAsExpected() {
    ServiceUserListWrapper dummies = mgmt.getDummyUserList();
    assertEquals(dummies.users.size(), 3);
  }

  @Test
  void whenSerializingAndDeserializingUser_GsonPluginWorksAsExpected() {
    ServiceUser user = createQuickDummyUser1_Caesar();
    String userSerialized = gson.toJson(user); // Serialize.
    ServiceUser userDeserialized = gson.fromJson(userSerialized, ServiceUser.class); // Deserialize.

    assertEquals(user, userDeserialized);
  }

  /**
   * Will assemble a wrapper of dummy users.\n
   * Will not be stored in memory.\n
   * Will not overwrite user file.
   *
   * @return A payload of dummy users.
   */
  public static ServiceUserListWrapper createDummyUserList() {
    ServiceUserListWrapper userList = new ServiceUserListWrapper();
    userList.users.add(createQuickDummyUser1_Caesar());
    userList.users.add(createQuickDummyUser2_Anders());
    userList.users.add(createQuickDummyUser3_Bertil());
    return userList;
  }

  public static ServiceUser createQuickDummyUser1_Caesar() {
    ServiceUser user = new ServiceUser(
        "Caesar",
        MyUserDetailsService.generateHashedPassword("qwerty"),
        // For specimen password "qwerty"...:
        // Stored as BCrypt-encode at strength 10 as "$2y$10$Ffs4rDCIok.I3uuQ8IIMxufD5FoTvhxymukqEBElHwRxEvaLy8dRO",
        // Sent over web, encoded as BASE64 it is: "SGVucmlrOnF3ZXJ0eQ=="
        "Caesar Julius",
        "NMT",
        "cc@a.aa",
        "073-1234567",
        Arrays.asList("USER", "ADMIN")
    );
    return user;
  }

  public static ServiceUser createQuickDummyUser2_Anders() {
    ServiceUser user = new ServiceUser(
        "Anders",
        MyUserDetailsService.generateHashedPassword("abcdefg"),
        "Anders Andersson",
        "NMT",
        "aa@a.aa",
        "073-1234567",
        Arrays.asList("USER")
    );
    return user;
  }

  // Do NOT use these Dummy users for actual service usage, for obvious security reasons.
  public static ServiceUser createQuickDummyUser3_Bertil() {
    ServiceUser user = new ServiceUser(
        "Bertil",
        MyUserDetailsService.generateHashedPassword("1234567"),
        "Bertil Bertilsson",
        "NMT",
        "bb@a.aa",
        "073-1234567",
        Arrays.asList("ADMIN")
    );
    return user;
  }
}
