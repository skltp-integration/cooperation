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
import tools.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A LogicalAddress Data Transfer Object
 *
 */
@JacksonXmlRootElement(localName = "logicalAddress")
@JsonInclude(Include.NON_EMPTY)
public class LogicalAddressDTO {

	private Long id;
	private String description;
	private String logicalAddress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogicalAddress() {
		return logicalAddress;
	}

	public void setLogicalAddress(String logicalAddress) {
		this.logicalAddress = logicalAddress;
	}
}
