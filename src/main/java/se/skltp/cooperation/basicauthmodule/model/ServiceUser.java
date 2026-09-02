/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
//////
// 2022-05-17, Henrik Augustsson.
// Nordic Medtest.
//////

package se.skltp.cooperation.basicauthmodule.model;

import org.jspecify.annotations.NonNull;
import se.skltp.cooperation.basicauthmodule.model.dto.UserData;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * A representation of a User entry for the app's security.
 */
@Entity
public final class ServiceUser {
	@NonNull @Id
	public String username;
	@NonNull
	public String password;
	@NonNull
	public String contactName;
	@NonNull
	public String contactOrganization;
	@NonNull
	public String contactMail;
	@NonNull
	public String contactPhone = null;

	@NonNull
	@ElementCollection(fetch = FetchType.EAGER, targetClass = String.class)
	@Column(name = "roles")
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "id"))
	public List<String> roles;

	public ServiceUser(@NonNull String username,
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
	public ServiceUser() {}

	public ServiceUser(UserData dto) {
		this.username = dto.username;
		this.password = dto.password;
		this.contactName = dto.contactName;
		this.contactOrganization = dto.contactOrganization;
		this.contactMail = dto.contactMail;
		this.contactPhone = dto.contactPhone;
		this.roles = dto.roles;
	}

	@NonNull
	public String getUsername() {
		return username;
	}

	@NonNull
	public String getPassword() {
		return password;
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

	@NonNull
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
			&& getPassword().equals(that.getPassword())
			&& getContactName().equals(that.getContactName())
			&& getContactOrganization().equals(that.getContactOrganization())
			&& getContactMail().equals(that.getContactMail())
			&& Objects.equals(getContactPhone(), that.getContactPhone())
			&& new HashSet<>(getRoles()).containsAll(that.getRoles());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUsername(), getPassword(), getContactName(), getContactOrganization(), getContactMail(), getContactPhone(), getRoles());
	}

	@Override
	public String toString() {
		return "ServiceUser{" +
			"username='" + username + '\'' +
			", password='" + password + '\'' +
			", contactName='" + contactName + '\'' +
			", contactOrganization='" + contactOrganization + '\'' +
			", contactMail='" + contactMail + '\'' +
			", contactPhone='" + contactPhone + '\'' +
			", roles=" + roles +
			'}';
	}
}
