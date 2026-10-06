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
import se.skltp.cooperation.domain.ServiceProduction;

/**
 * A ServiceProduction Data Transfer Object with associations
 *
 */
@JsonRootName("serviceProduction")
@JsonInclude(Include.NON_EMPTY)
public record ServiceProductionDTO (
	Long id,
	String physicalAddress,
	String rivtaProfile,

	ServiceProducerDTO serviceProducer,
	LogicalAddressDTO logicalAddress,
	ConnectionPointDTO connectionPoint,
	ServiceContractDTO serviceContract
) {
	public static ServiceProductionDTO from(ServiceProduction input) {
		return new ServiceProductionDTO(
			input.getId(),
			input.getPhysicalAddress(),
			input.getRivtaProfile(),

			input.getServiceProducer() == null
				? null
				: ServiceProducerDTO.from(input.getServiceProducer()),

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
