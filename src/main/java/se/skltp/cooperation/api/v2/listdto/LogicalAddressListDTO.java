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
