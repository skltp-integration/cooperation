//////
// 2022-05-17, Henrik Augustsson.
// Nordic Medtest.
//////

package se.skltp.cooperation.basicauthmodule.model.dto;

import org.springframework.lang.NonNull;
import se.skltp.cooperation.basicauthmodule.MyUserDetailsService;

import java.util.List;

/**
 * A representation of a User entry for the app's security.
 */
public final class UserData {
	public String username;
	public String password;
	public String contactName;
	public String contactOrganization;
	public String contactMail;
	public String contactPhone;
	public List<String> roles;

	public UserData(@NonNull String username,
					@NonNull String password,
					@NonNull String contactName,
					@NonNull String contactOrganization,
					@NonNull String contactMail,
					String contactPhone,
					@NonNull List<String> roles) {
		this.username = username;
		this.password = password;
		this.contactName = contactName;
		this.contactOrganization = contactOrganization;
		this.contactMail = contactMail;
		this.contactPhone = contactPhone;
		this.roles = roles;
	}
	public UserData() {}

	/**
	 * Uses the password checker in MyUserDetailsService to check basic requirements.
	 * @return true if the password is too crummy.
	 */
	public boolean hasBadPassword() {
		return MyUserDetailsService.isBadPassword(this.password);
	}
}
