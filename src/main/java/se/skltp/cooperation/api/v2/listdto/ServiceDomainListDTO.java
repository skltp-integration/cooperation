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

import se.skltp.cooperation.api.v2.dto.ServiceDomainDTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A wrapper object to hold a list of {@link ServiceDomainDTO} objects.
 *
 * @author Jan Vasternas
 */
@JacksonXmlRootElement(localName="serviceDomain")
public class ServiceDomainListDTO {

	@JacksonXmlProperty(localName = "serviceDomain")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ServiceDomainDTO> serviceDomains = new ArrayList<>();

	public ServiceDomainListDTO(List<ServiceDomainDTO> serviceDomains) {
		super();
		this.serviceDomains = serviceDomains;
	}

	@XmlElement(name = "serviceDomain")
	public List<ServiceDomainDTO> getServiceDomains() {
		return serviceDomains;
	}

	public void setServiceDomains(List<ServiceDomainDTO> serviceDomains) {
		this.serviceDomains = serviceDomains;
	}
}
