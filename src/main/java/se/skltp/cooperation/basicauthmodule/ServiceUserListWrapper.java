//////
// 2022-05-17, Henrik Augustsson.
// Nordic Medtest.
//////

package se.skltp.cooperation.basicauthmodule;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple payload list wrapper for user entries.
 * Could be expanded with further metadata.
 */
public final class ServiceUserListWrapper {
	public List<ServiceUser> users = new ArrayList<>();
}
