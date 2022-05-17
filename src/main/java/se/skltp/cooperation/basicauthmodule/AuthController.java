//////
// 2022-05-17, Henrik Augustsson.
// Nordic Medtest.
//////

package se.skltp.cooperation.basicauthmodule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authoring")
public final class AuthController {

  @Autowired
  ServiceUserManagement users;

  ////
  // Pings and Hellos.
  ////

  @GetMapping("/ping")
  public String hello() {
    return "Pong!";
  }
  @GetMapping("/admin/hello")
  public String helloAdmin() {
    return "Hello Admin!";
  }
  @GetMapping("/user/hello")
  public String helloUser() {
    return "Hello User!";
  }

  ////
  // Dummy and Sample Users.
  ////

  @GetMapping("/admin/get_dummies_template")
  public ServiceUserListWrapper retrieveDummyUsers() {
    return users.retrieveDummyUsers();
  }

  @PostMapping("/admin/create_dummies_file_respond_content")
  public String createDummyUsers() {
    return users.setupDummyUserFile();
  }

  ////
  // ADMINISTRATIVE
  ////

  @PostMapping("/admin/trigger_read_user_file")
  public String readUserFile() {
    users.triggerFileRead();
    return "File read triggered, and done!\n" +
        "This call will not be replied to with user file content.";
  }

  //@GetMapping("/admin/generate_crypto") // TODO: Enable if you want a convenient BCrypt generator.
  public String getHash(@RequestParam String rawpassword) {
    return "Provided string \"" + rawpassword + "\" has been 'Bcrypted' as:\n" +
        MyUserDetailsService.generateBCryptHashedPassword(rawpassword);
  }

  // TODO: Implement if desirable.
  @GetMapping("/admin/get_users")
  public ServiceUserListWrapper getUsers() {
    return null;
  }

  ////
  // Debug.
  ////

  @GetMapping("/admin/serialize_test")
  public String serializationTest() {
    return users.serializationTest();
  }
}
