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
import se.skltp.cooperation.basicauthmodule.model.dto.UserData;
import se.skltp.cooperation.basicauthmodule.model.ServiceUserListWrapper;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Service
public final class ServiceUserManagement {

	private final Logger log = LoggerFactory.getLogger(ServiceUserManagement.class);

	@Autowired
	UserRepository userRepository;

	private static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

	@PostConstruct
	private void initialization() {
		log.debug("INITIALIZATION of ServiceUserManagement instance.");
		if (userRepository.findAll().size() == 0) {
			log.info("ADDING CAESAR as generic auth user. Change in DB.");
			createUserFlow(new UserData(
				"Caesar",
				"Qwerty123",
				"Caesar Julius",
				"NMT",
				"cj@a.aa",
				"073-1234567",
				Arrays.asList(Settings.REG_USER_ROLE, Settings.REG_ADMIN_ROLE, Settings.AUTH_ADMIN_ROLE)
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

	public ServiceUserListWrapper findAllUsersProcessed() {
		ServiceUserListWrapper userList = new ServiceUserListWrapper();
		for(ServiceUser user: userRepository.findAll()) {
			userList.getUsers().add(new ServiceUser(
				user.username,
				Settings.REDACTED_LABEL,
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

	public ServiceUser createUserFlow(UserData newUserPayload) {
		// At the entry of this function, it is already known that the user does not exist.
		// Any permission settings were checked in the controller.

		log.info("Attempting CREATION of USER " + newUserPayload.username + " with roles: " + newUserPayload.roles);

		newUserPayload.password = MyUserDetailsService.generateHashedPassword(newUserPayload.password);

		ServiceUser saved = userRepository.saveAndFlush(new ServiceUser(newUserPayload));
		saved.password = Settings.REDACTED_LABEL;
		return saved;
	}

	public ServiceUser editUserFlow(UserData incomingUserData, ServiceUser existingUser) {
		// At the entry of this function, it is already known that the user exists.
		//   The specific user entry was located in the controller function, and attached to this call.
		// Any permission settings were checked in the controller.

		log.info("Attempting EDIT of USER " + incomingUserData.username + ", which will have provided roles: " + incomingUserData.roles);

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
		saved.password = Settings.REDACTED_LABEL;
		return saved;
	}
	public ServiceUser changePasswordFlow(ServiceUser existingUser, String newPassword) {
		// At the entry of this function, password quality has been checked,
		//    and any permission settings were checked in the controller.

		log.info("Attempting PWD CHANGE on USER " + existingUser.username + " with current roles: " + existingUser.roles);

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
		saved.password = Settings.REDACTED_LABEL;
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
		userList.getUsers().add(new ServiceUser(
			"Caesar",
			Settings.REDACTED_LABEL,
			// For specimen password "qwerty"...:
			// Stored as BCrypt-encode at strength 10 as "$2y$10$Ffs4rDCIok.I3uuQ8IIMxufD5FoTvhxymukqEBElHwRxEvaLy8dRO",
			// Sent over web, encoded as BASE64 it is: "SGVucmlrOnF3ZXJ0eQ=="
			"Caesar Julius",
			"NMT",
			"cj@a.aa",
			"073-1234567",
			Arrays.asList(Settings.REG_USER_ROLE, Settings.REG_ADMIN_ROLE, Settings.AUTH_ADMIN_ROLE)
		));
		userList.getUsers().add(new ServiceUser(
			"Anders",
			Settings.REDACTED_LABEL,
			"Anders Adminsson",
			"NMT",
			"aa@a.aa",
			"073-9876543",
			Arrays.asList(Settings.REG_USER_ROLE, Settings.REG_ADMIN_ROLE)
		));
		userList.getUsers().add(new ServiceUser(
			"Uffe",
			Settings.REDACTED_LABEL,
			"Uffe Usersson",
			"NMT",
			"uu@a.aa",
			"073-2468013",
			Collections.singletonList(Settings.REG_USER_ROLE)
		));
		return userList;
	}
}
