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

import se.skltp.cooperation.web.rest.v1.dto.ServiceConsumerDTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A wrapper object to hold a list of {@link se.skltp.cooperation.web.rest.v1.dto.ServiceConsumerDTO} objects.
 *
 * @author Peter Merikan
 */
@JacksonXmlRootElement(localName="serviceConsumers")
public class ServiceConsumerListDTO {

	@JacksonXmlProperty(localName = "serviceConsumer")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ServiceConsumerDTO> serviceConsumers = new ArrayList<>();

	public List<ServiceConsumerDTO> getServiceConsumers() {
		return serviceConsumers;
	}

	public void setServiceConsumers(List<ServiceConsumerDTO> serviceConsumers) {
		this.serviceConsumers = serviceConsumers;
	}
}
