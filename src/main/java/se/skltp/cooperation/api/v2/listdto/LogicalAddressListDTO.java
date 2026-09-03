/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.v2.listdto;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;

import se.skltp.cooperation.api.v2.dto.LogicalAddressDTO;

import com.fasterxml.jackson.annotation.JsonRootName;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * A wrapper object to hold a list of {@link LogicalAddressDTO} objects.
 *
 * @author Jan Vasternas
 */
@JsonRootName("logicalAddresss")
public class LogicalAddressListDTO {

	@JacksonXmlProperty(localName = "logicalAddress")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<LogicalAddressDTO> logicalAddresss = new ArrayList<>();

	@XmlElement(name = "logicalAddress")
	public List<LogicalAddressDTO> getLogicalAddresss() {
		return logicalAddresss;
	}

	public void setLogicalAddresss(List<LogicalAddressDTO> logicalAddresss) {
		this.logicalAddresss = logicalAddresss;
	}

}
