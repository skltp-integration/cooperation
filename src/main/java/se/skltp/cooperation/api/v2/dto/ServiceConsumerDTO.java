/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.v2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;
import se.skltp.cooperation.domain.ServiceConsumer;

/**
 * A ServiceConsumer Data Transfer Object
 *
 */
@JsonRootName("serviceConsumer")
@JsonInclude(Include.NON_EMPTY)
public record ServiceConsumerDTO(
	Long id,
	String description,
	String hsaId,
	ConnectionPointDTO connectionPoint
) {
	public static ServiceConsumerDTO from(ServiceConsumer input) {
		return new ServiceConsumerDTO(
			input.getId(),
			input.getDescription(),
			input.getHsaId(),
			input.getConnectionPoint() == null
				? null
				: ConnectionPointDTO.from(input.getConnectionPoint())
		);
	}
}
