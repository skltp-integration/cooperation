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
