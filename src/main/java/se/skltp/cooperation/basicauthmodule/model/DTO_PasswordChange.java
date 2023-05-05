package se.skltp.cooperation.basicauthmodule.model;

public class DTO_PasswordChange {
	public String username;
	public String newPassword;

	public DTO_PasswordChange() {}
	public DTO_PasswordChange(String username, String newPassword) {
		this.username = username;
		this.newPassword = newPassword;
	}
}
