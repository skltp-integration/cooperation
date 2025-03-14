//////
// 2022-05-17, Henrik Augustsson.
// Nordic Medtest.
//////

package se.skltp.cooperation.basicauthmodule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import se.skltp.cooperation.basicauthmodule.model.ServiceUser;

import java.util.ArrayList;

public final class MyUserDetailsService implements UserDetailsService {

	@Autowired
	ServiceUserManagement userManagement;

	@Override
	public User loadUserByUsername(String username) {
		ServiceUser user = userManagement.findUser(username);

		ArrayList<SimpleGrantedAuthority> grants = new ArrayList<>();
		for (String role : user.roles
		) {
			SimpleGrantedAuthority grant = new SimpleGrantedAuthority(role);
			grants.add(grant);
		}

		return new User(user.username, user.password, grants);
	}

	public static String generateHashedPassword(String rawPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		return passwordEncoder.encode(rawPassword);
	}

	public static boolean isBadPassword(String password) {
		return
			password == null
				|| !password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
	}
}
