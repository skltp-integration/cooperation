//////
// 2022-05-17, Henrik Augustsson.
// Nordic Medtest.
//////

package se.skltp.cooperation.basicauthmodule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(Settings.authAdministrationSubPath)
public final class AuthController {

	@Autowired
	ServiceUserManagement users;

	@Autowired
	Settings settings;

	////
	// Pings and Hellos.
	////

	@GetMapping("/ping")
	public String hello() {
		if (!settings.allowEndpoint_hellosAndPings) {
			throw new ResponseStatusException(
				HttpStatus.LOCKED, "Not Available."
			);
		}
		return "Pong!";
	}

	@GetMapping("/admin/hello")
	public String helloAdmin() {
		if (!settings.allowEndpoint_hellosAndPings) {
			throw new ResponseStatusException(
				HttpStatus.LOCKED, "Not Available."
			);
		}
		return "Hello Admin!";
	}

	@GetMapping("/user/hello")
	public String helloUser() {
		if (!settings.allowEndpoint_hellosAndPings) {
			throw new ResponseStatusException(
				HttpStatus.LOCKED, "Not Available."
			);
		}
		return "Hello User!";
	}

	////
	// Dummy and Sample Users.
	////

	@GetMapping("/admin/get_dummies_template")
	public ServiceUserListWrapper retrieveDummyUsers() {
		if (!settings.allowEndpoint_downloadSampleUserFile) {
			throw new ResponseStatusException(
				HttpStatus.LOCKED, "Not Available."
			);
		}

		return users.retrieveDummyUsers();
	}

	@PostMapping("/admin/create_dummies_file_respond_content")
	public String createDummyUsers() {

		if (!settings.allowEndpoint_resetUserFile) {
			throw new ResponseStatusException(
				HttpStatus.LOCKED, "Not Available."
			);
		}

		return users.setupDummyUserFile();
	}

	////
	// ADMINISTRATIVE
	////

	@PostMapping("/admin/trigger_read_user_file")
	public String readUserFile() {

		if (!settings.allowEndpoint_rescanUserFile) {
			throw new ResponseStatusException(
				HttpStatus.LOCKED, "Not Available."
			);
		}

		users.triggerFileRead();

		return "File read triggered, and done!\n" +
			"This call will not be replied to with user file content.";
	}

	@GetMapping("/admin/generate_crypto") // TODO: Enable if you want a convenient BCrypt generator.
	public String getHash(@RequestParam String rawpassword) {
		if (!settings.allowEndpoint_generateCryptHash) {
			throw new ResponseStatusException(
				HttpStatus.LOCKED, "Not Available."
			);
		}

		return "Provided string \"" + rawpassword + "\" has been 'Bcrypted' as:\n" +
			MyUserDetailsService.generateBCryptHashedPassword(rawpassword);
	}

	// TODO: Implement if desirable.
	@GetMapping("/admin/get_users")
	public ServiceUserListWrapper getUsers() {
		if (!settings.allowEndpoint_downloadUsers) {
			throw new ResponseStatusException(
				HttpStatus.LOCKED, "Not Available."
			);
		}

		throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not Available.");
	}

	////
	// Debug.
	////

	@GetMapping("/admin/serialize_test")
	public String serializationTest() {
		if (!settings.allowEndpoint_testSerialization) {
			throw new ResponseStatusException(
				HttpStatus.LOCKED, "Not Available."
			);
		}

		return users.serializationTest();
	}
}
