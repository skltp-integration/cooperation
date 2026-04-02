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

import se.skltp.cooperation.api.v2.dto.ServiceContractDTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A wrapper object to hold a list of {@link ServiceContractDTO} objects.
 *
 * @author Jan Vasternas
 */
@JacksonXmlRootElement(localName="serviceContracts")
public class ServiceContractListDTO {

	@JacksonXmlProperty(localName = "serviceContract")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ServiceContractDTO> serviceContracts = new ArrayList<>();

	@XmlElement(name = "serviceContract")
	public List<ServiceContractDTO> getServiceContracts() {
		return serviceContracts;
	}

	public void setServiceContracts(List<ServiceContractDTO> serviceContracts) {
		this.serviceContracts = serviceContracts;
	}
}
