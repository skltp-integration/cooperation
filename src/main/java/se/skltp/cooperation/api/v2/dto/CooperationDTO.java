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
import se.skltp.cooperation.domain.Cooperation;

/**
 * A Cooperation Data Transfer Object with associations
 *
 */

@JsonRootName("cooperation")
@JsonInclude(Include.NON_EMPTY)
public record CooperationDTO(
	Long id,

	ServiceConsumerDTO serviceConsumer,
	LogicalAddressDTO logicalAddress,
	ConnectionPointDTO connectionPoint,
	ServiceContractDTO serviceContract
) {
	public static CooperationDTO from(Cooperation input) {
		return new CooperationDTO(
			input.getId(),

			input.getServiceConsumer() == null
				? null
				: ServiceConsumerDTO.from(input.getServiceConsumer()),

			input.getLogicalAddress() == null
				? null
				: LogicalAddressDTO.from(input.getLogicalAddress()),

			input.getConnectionPoint() == null
				? null
				: ConnectionPointDTO.from(input.getConnectionPoint()),

			input.getServiceContract() == null
				? null
				: ServiceContractDTO.from(input.getServiceContract())
		);
	}
}
