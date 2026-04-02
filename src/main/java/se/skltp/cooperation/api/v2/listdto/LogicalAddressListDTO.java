/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.v2.listdto;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;

import se.skltp.cooperation.api.v2.dto.LogicalAddressDTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A wrapper object to hold a list of {@link LogicalAddressDTO} objects.
 *
 * @author Jan Vasternas
 */
@JacksonXmlRootElement(localName="logicalAddresss")
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
