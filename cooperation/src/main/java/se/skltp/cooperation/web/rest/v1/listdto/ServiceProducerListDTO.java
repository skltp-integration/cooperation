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
package se.skltp.cooperation.web.rest.v1.listdto;

import java.util.ArrayList;
import java.util.List;

import se.skltp.cooperation.web.rest.v1.dto.ServiceProducerDTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A wrapper object to hold a list of {@link se.skltp.cooperation.web.rest.v1.dto.ServiceProducerDTO} objects.
 *
 * @author Peter Merikan
 */
@JacksonXmlRootElement(localName="serviceProducers")
public class ServiceProducerListDTO {

	@JacksonXmlProperty(localName = "serviceProducer")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ServiceProducerDTO> serviceProducers = new ArrayList<>();

	public List<ServiceProducerDTO> getServiceProducers() {
		return serviceProducers;
	}

	public void setServiceProducers(List<ServiceProducerDTO> serviceProducers) {
		this.serviceProducers = serviceProducers;
	}
}
