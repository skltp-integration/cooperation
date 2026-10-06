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
import se.skltp.cooperation.domain.ServiceContract;


/**
 * A ServiceContract Data Transfer Object
 * Minor version in omitted from this class and the rest output
 *
 */
@JsonRootName("serviceContract")
@JsonInclude(Include.NON_EMPTY)
public record ServiceContractDTO (
	Long id,
	String name,
	String namespace,
	Integer major,
	Integer minor
) {
	public static ServiceContractDTO from(ServiceContract input) {
		return new ServiceContractDTO(
			input.getId(),
			input.getName(),
			input.getNamespace(),
			input.getMajor(),
			input.getMinor()
		);
	}
}
