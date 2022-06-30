//////
// 2022-05-17, Henrik Augustsson.
// Nordic Medtest.
//////

package se.skltp.cooperation.basicauthmodule;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

@Service
public final class ServiceUserManagement {

	@Autowired
	Settings settings;

	private final HashMap<String, ServiceUser> allKnownUsers;
	private static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();



	/**
	 * Constructor. Sets up space for holding users in memory.
	 */
	private ServiceUserManagement() {
		this.allKnownUsers = new HashMap<>();
	}

	@PostConstruct
	private void initialization() {
		this.readUserFile();
	}

	////
	// USER FILE HANDLING
	////

	/**
	 * Triggers a read of the user file.
	 * Can be triggered manually, but will also run periodically once per hour (cron-able expression).
	 */
	@Scheduled(cron = "${settings.fileReadCron:0 5 * * * *}")
	public void triggerFileRead() {
		readUserFile();
	}

	/**
	 * Does the practical reading of the user file, and adds any users found into this class' user list.
	 */
	private void readUserFile() {
		try {
			Files.createDirectories(Paths.get(settings.folderPath));

			Path path = Paths.get(settings.getFilePath());
			if (!Files.exists(path)) {
				setupDummyUserFile();
			}

			String fileContentJSON = new String(Files.readAllBytes(Paths.get(settings.getFilePath())));
			ServiceUserListWrapper usersFromFile = gson.fromJson(fileContentJSON, ServiceUserListWrapper.class);

			this.allKnownUsers.clear();
			for (ServiceUser user : usersFromFile.users) {
				this.addServiceUser(user);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	///
	// GET- & SETTERS
	///


	/**
	 * Checks if User is present by name.
	 * @param username
	 * @return
	 */
	public boolean hasServiceUser(String username) {
		return allKnownUsers.containsKey(username);
	}

	/**
	 * Retrieves the information held for one user by username.
	 *
	 * @param username The id and username of the user.
	 * @return The data entry of the user, if found in the in-memory user list.\n
	 * @throws ResponseStatusException Will raise a 404 if the username is not found.
	 */
	public ServiceUser getServiceUser(String username) {
		if (allKnownUsers.containsKey(username)) {
			return allKnownUsers.get(username);
		} else {
			throw new ResponseStatusException(
				HttpStatus.NOT_FOUND, "user not found."
			);
		}
	}

	/**
	 * Records one user entry into the in-memory user list.
	 */
	public void addServiceUser(ServiceUser user) {
		if (!this.allKnownUsers.containsKey(user.username)) {
			this.allKnownUsers.put(user.username, user);
		} else {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Entity ID already exists.");
		}
	}

	public void dropServiceUser(String username) {
		if (this.allKnownUsers.containsKey(username)) {
			this.allKnownUsers.remove(username);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity ID not found for deletion.");
		}
	}


	////
	//  DUMMIES / SAMPLE USERS.
	////

	/**
	 * Will generate a user file containing a handful of sample users.\n
	 * WARNING: Will overwrite user file.
	 *
	 * @return Responds with the content written to file.
	 */
	public String setupDummyUserFile() {

		String content = retrieveDummyUsersAsJSON();

		try {
			Files.createDirectories(Paths.get(settings.folderPath));

			Path path = Paths.get(settings.getFilePath());
			if (!Files.exists(path)) {
				Files.createFile(path);
			}
			FileWriter file = new FileWriter(settings.getFilePath());
			file.write(content);
			file.flush();
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		triggerFileRead(); // Re-scan the newly generated file into user list.

		return content;
	}

	/**
	 * Will create a wrapper with a list of dummy users.\n
	 * Will not be stored in memory.\n
	 * Will not overwrite user file.
	 *
	 * @return A payload of dummy users.
	 */
	public ServiceUserListWrapper retrieveDummyUsers() {
		return createDummyUserList();
	}

	/**
	 * Will create a wrapper with a list of dummy users, and then serialize it.\n
	 * Will not be stored in memory.\n
	 * Will not overwrite user file.
	 *
	 * @return A serialized string of the payload of dummy users.
	 */
	private String retrieveDummyUsersAsJSON() {
		ServiceUserListWrapper userList = createDummyUserList();
		String serialized = gson.toJson(userList);
		return serialized;
	}

	/**
	 * Will assemble a wrapper of dummy users.\n
	 * Will not be stored in memory.\n
	 * Will not overwrite user file.
	 *
	 * @return A payload of dummy users.
	 */
	public ServiceUserListWrapper createDummyUserList() {
		ServiceUserListWrapper userList = new ServiceUserListWrapper();
		userList.users.add(getQuickDummyUser1());
		// userList.users.add(getQuickDummyUser2());
		// userList.users.add(getQuickDummyUser3());
		return userList;
	}

	// Do NOT use these Dummy users for actual service usage, for obvious security reasons.
	private ServiceUser getQuickDummyUser1() {
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
}
