package se.skltp.cooperation.basicauthmodule.model.dto;

public class PasswordChange {
	public String username;
	public String newPassword;

	public PasswordChange() {}
	public PasswordChange(String username, String newPassword) {
		this.username = username;
		this.newPassword = newPassword;
	}

}
