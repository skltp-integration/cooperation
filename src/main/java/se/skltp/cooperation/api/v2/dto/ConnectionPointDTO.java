/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.v2.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;
import se.skltp.cooperation.domain.ConnectionPoint;

/**
 * A ConnectionPoint Data Transfer Object
 *
 */
@JsonRootName("connectionPoint")
@JsonInclude(Include.NON_EMPTY)
public record ConnectionPointDTO(
	Long id,
	String platform,
	String environment,
	@JsonFormat(
		shape = JsonFormat.Shape.STRING,
		pattern = "yyyy-MM-dd'T'HH:mm:ssZ",
		timezone = "CET"
	)
	Date snapshotTime
) {
	public static ConnectionPointDTO from(ConnectionPoint conn) {
		return new ConnectionPointDTO(
			conn.getId(),
			conn.getPlatform(),
			conn.getEnvironment(),
			conn.getSnapshotTime()
		);
	}
}
