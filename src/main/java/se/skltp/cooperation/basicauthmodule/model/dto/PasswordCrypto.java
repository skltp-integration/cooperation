/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.basicauthmodule.model.dto;

public class PasswordCrypto {
	public String rawPassword;

	public PasswordCrypto(String rawPassword) {
		this.rawPassword = rawPassword;
	}

	public PasswordCrypto() {
	}
}
