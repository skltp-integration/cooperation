//////
// 2022-05-17, Henrik Augustsson.
// Nordic Medtest.
//////

package se.skltp.cooperation.basicauthmodule;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * A representation of a User entry for the app's security.
 */
public final class ServiceUser {
	@NonNull
	public String username;
	@NonNull
	public String bCryptHash;
	@NonNull
	public String contactName;
	@NonNull
	public String contactOrganization;
	@NonNull
	public String contactMail;

	public String contactPhone = null;
	@NonNull
	public List<String> roles;

	public ServiceUser(@NonNull String username,
					   @NonNull String bCryptHash,
					   @NonNull String contactName,
					   @NonNull String contactOrganization,
					   @NonNull String contactMail,
					   String contactPhone,
					   @NonNull List<String> roles) {
		this.username = username;
		this.bCryptHash = bCryptHash;
		this.contactName = contactName;
		this.contactOrganization = contactOrganization;
		this.contactMail = contactMail;
		this.contactPhone = contactPhone;
		this.roles = roles;
	}

	@NonNull
	public String getUsername() {
		return username;
	}

	@NonNull
	public String getbCryptHash() {
		return bCryptHash;
	}

	@NonNull
	public String getContactName() {
		return contactName;
	}

	@NonNull
	public String getContactOrganization() {
		return contactOrganization;
	}

	@NonNull
	public String getContactMail() {
		return contactMail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	@NonNull
	public List<String> getRoles() {
		return roles;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ServiceUser that = (ServiceUser) o;
		return getUsername().equals(that.getUsername())
			&& getbCryptHash().equals(that.getbCryptHash())
			&& getContactName().equals(that.getContactName())
			&& getContactOrganization().equals(that.getContactOrganization())
			&& getContactMail().equals(that.getContactMail())
			&& Objects.equals(getContactPhone(), that.getContactPhone())
			&& getRoles().equals(that.getRoles());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUsername(), getbCryptHash(), getContactName(), getContactOrganization(), getContactMail(), getContactPhone(), getRoles());
	}
}
