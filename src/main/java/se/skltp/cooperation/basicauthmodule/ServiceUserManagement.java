//////
// 2022-05-17, Henrik Augustsson.
// Nordic Medtest.
//////

package se.skltp.cooperation.basicauthmodule;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.skltp.cooperation.basicauthmodule.model.ServiceUser;
import se.skltp.cooperation.basicauthmodule.model.DTO_UserData;
import se.skltp.cooperation.basicauthmodule.model.ServiceUserListWrapper;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Service
public final class ServiceUserManagement {

	private final Logger log = LoggerFactory.getLogger(ServiceUserManagement.class);

	//@Autowired
	//Settings settings;

	@Autowired
	UserRepository userRepository;

	private static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

	@PostConstruct
	private void initialization() {
		System.out.println("INITIALIZATION");
		if (userRepository.findAll().size() == 0) {
			System.out.println("ADD CAESAR");
			createUserFlow(new DTO_UserData(
				"Caesar",
				"Qwerty123",
				// For specimen password "qwerty"...:
				// Stored as BCrypt-encode at strength 10 as "$2y$10$Ffs4rDCIok.I3uuQ8IIMxufD5FoTvhxymukqEBElHwRxEvaLy8dRO",
				// Sent over web, encoded as BASE64 it is: "SGVucmlrOnF3ZXJ0eQ=="
				"Caesar Julius",
				"NMT",
				"cj@a.aa",
				"073-1234567",
				Arrays.asList("USER", "ADMIN", "SUPER_ADMIN")
			));
		}
	}

	////
	// *** DATABASE BASED SOLUTIONS ***
	////

	///
	// GET- & SETTERS
	///

	/**
	 * Retrieves the information held for one user by username.
	 *
	 * @param username The username (id) of the user.
	 * @return The data entry of the user, if found in the in-memory user list.\n
	 * @throws ResponseStatusException Will raise a 404 if the username is not found.
	 */
	public ServiceUser findUser(String username) {
		if (userRepository.existsById(username)) {
			Optional<ServiceUser> userOpt = userRepository.findById(username);
			if (userOpt.isPresent()) {
				return userOpt.get();
			}
		}

		// Fall-through exception.
		throw new ResponseStatusException(
			HttpStatus.NOT_FOUND, "user not found."
		);
	}

	public ServiceUserListWrapper findAllUsersRaw() {
		ServiceUserListWrapper userList = new ServiceUserListWrapper();

		userList.users.addAll(
			userRepository.findAll()
		);

		return userList;
	}
	public ServiceUserListWrapper findAllUsersProcessed() {
		ServiceUserListWrapper userList = new ServiceUserListWrapper();
		for(ServiceUser user: userRepository.findAll()) {
			userList.users.add(new ServiceUser(
				user.username,
				"[redacted]",
				user.contactName,
				user.contactOrganization,
				user.contactMail,
				user.contactPhone,
				user.roles
			));
		}
		return userList;
	}

	/**
	 * Checks if User is present by name.
	 * @param username The username (id) of the user.
	 * @return true if user exists in the db, false otherwise.
	 */
	public boolean userExists(String username) {
		return userRepository.existsById(username);
	}

	public ServiceUser createUserFlow(DTO_UserData newUserPayload) {
		// At the entry of this function, it is already known that the user does not exist.
		// Any permission settings were checked in the controller.


		newUserPayload.password = MyUserDetailsService.generateHashedPassword(newUserPayload.password);

		ServiceUser saved = userRepository.saveAndFlush(new ServiceUser(newUserPayload));
		saved.password = "[redacted]";
		return saved;
	}

	public ServiceUser editUserFlow(DTO_UserData incomingUserData, ServiceUser existingUser) {
		// At the entry of this function, it is already known that the user exists.
		//   The specific user entry was located in the controller function, and attached to this call.
		// Any permission settings were checked in the controller.

		ServiceUser editedUser = new ServiceUser(
			existingUser.username,
			existingUser.getPassword(),
			incomingUserData.contactName,
			incomingUserData.contactOrganization,
			incomingUserData.contactMail,
			incomingUserData.contactPhone,
			incomingUserData.roles
		);

		ServiceUser saved = userRepository.saveAndFlush(editedUser);
		saved.password = "[redacted]";
		return saved;
	}
	public ServiceUser changePasswordFlow(ServiceUser existingUser, String newPassword) {
		// At the entry of this function, password quality has been checked,
		//    and any permission settings were checked in the controller.

		String hashedPassword = MyUserDetailsService.generateHashedPassword(newPassword);

		ServiceUser pwdChangedUser = new ServiceUser(
			existingUser.username,
			hashedPassword,
			existingUser.contactName,
			existingUser.contactOrganization,
			existingUser.contactMail,
			existingUser.contactPhone,
			existingUser.roles
		);

		ServiceUser saved = userRepository.saveAndFlush(pwdChangedUser);
		saved.password = "[redacted]";
		return saved;
	}

	void deleteUserTest(String username) {
		if (!userRepository.existsById(username)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for deletion.");
		}

		userRepository.deleteById(username);
	}


	////
	//  DUMMIES / SAMPLE USERS.
	////

	/**
	 * Will assemble a wrapper of dummy users.\n
	 * Will not be stored in memory.\n
	 * Will not overwrite user file.
	 * Do NOT use these Dummy users for actual service usage, for obvious security reasons.
	 *
	 * @return A payload of dummy users.
	 */
	public ServiceUserListWrapper getDummyUserList() {
		ServiceUserListWrapper userList = new ServiceUserListWrapper();
		userList.users.add(new ServiceUser(
			"Caesar",
			"[redacted]",
			// For specimen password "qwerty"...:
			// Stored as BCrypt-encode at strength 10 as "$2y$10$Ffs4rDCIok.I3uuQ8IIMxufD5FoTvhxymukqEBElHwRxEvaLy8dRO",
			// Sent over web, encoded as BASE64 it is: "SGVucmlrOnF3ZXJ0eQ=="
			"Caesar Julius",
			"NMT",
			"cj@a.aa",
			"073-1234567",
			Arrays.asList("USER", "ADMIN", "SUPER_ADMIN")
		));
		userList.users.add(new ServiceUser(
			"Anders",
			"[redacted]",
			"Anders Adminsson",
			"NMT",
			"aa@a.aa",
			"073-9876543",
			Arrays.asList("USER", "ADMIN")
		));
		userList.users.add(new ServiceUser(
			"Uffe",
			"[redacted]",
			"Uffe Usersson",
			"NMT",
			"uu@a.aa",
			"073-2468013",
			Collections.singletonList("USER")
		));
		return userList;
	}
}
