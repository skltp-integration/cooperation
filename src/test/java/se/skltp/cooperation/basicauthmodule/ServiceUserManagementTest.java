package se.skltp.cooperation.basicauthmodule;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ServiceUserManagementTest {

  @Autowired
  ServiceUserManagement mgmt;

  private static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

  @Test
  void triggerFileRead() {
    assertDoesNotThrow(mgmt::triggerFileRead);
  }

  @Test
  void whenAddingAndDeletingUser_worksAsExpected() {
    ServiceUser testUser = createQuickDummyUser1_Caesar();

    if (mgmt.hasServiceUser(testUser.username)) {
      testUser.username = "DGA9FEK2NVYPSA7MCRHB3VEREE85KK";
    }

    mgmt.addServiceUser(testUser);
    assertTrue(mgmt.hasServiceUser(testUser.username));

    ServiceUser userStored = mgmt.getServiceUser(testUser.username);
    assertEquals(userStored, testUser);

    mgmt.dropServiceUser(testUser.username);
    assertFalse(mgmt.hasServiceUser(testUser.username));
  }

  @Test
  void whenCreatingDummmyUsers_AddAndRetrieveUsers_usersAreAsExpected() {
    ServiceUserListWrapper dummies = mgmt.createDummyUserList();
    assertEquals(dummies.users.size(), 1);
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
        MyUserDetailsService.generateBCryptHashedPassword("qwerty"),
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
        MyUserDetailsService.generateBCryptHashedPassword("abcdefg"),
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
        MyUserDetailsService.generateBCryptHashedPassword("1234567"),
        "Bertil Bertilsson",
        "NMT",
        "bb@a.aa",
        "073-1234567",
        Arrays.asList("ADMIN")
    );
    return user;
  }
}
