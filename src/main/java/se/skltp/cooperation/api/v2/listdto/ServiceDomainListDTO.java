/**
 * Copyright (c) 2014 Center for eHalsa i samverkan (CeHis).
 * 								<http://cehis.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package se.skltp.cooperation.api.v2.listdto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

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
