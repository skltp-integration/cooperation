//////
// 2022-05-17, Henrik Augustsson.
// Nordic Medtest.
//////

package se.skltp.cooperation.basicauthmodule.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple payload list wrapper for user entries.
 * Could be expanded with further metadata.
 */
public final class ServiceUserListWrapper {
	private List<ServiceUser> users = new ArrayList<>();

	public List<ServiceUser> getUsers() {
		return users;
	}

	public void setUsers(List<ServiceUser> users) {
		this.users = users;
	}
}
