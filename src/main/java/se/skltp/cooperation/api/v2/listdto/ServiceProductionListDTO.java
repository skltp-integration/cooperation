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

import se.skltp.cooperation.api.v2.dto.ServiceProductionDTO;

import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import tools.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A wrapper object to hold a list of {@link ServiceProductionDTO} objects.
 *
 */
@JacksonXmlRootElement(localName="serviceProductions")
public class ServiceProductionListDTO {

	@JacksonXmlProperty(localName = "serviceProduction")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ServiceProductionDTO> serviceProductions = new ArrayList<>();

	@XmlElement(name = "serviceProduction")
	public List<ServiceProductionDTO> getServiceProductions() {
		return serviceProductions;
	}

	public void setServiceProductions(List<ServiceProductionDTO> serviceProductions) {
		this.serviceProductions = serviceProductions;
	}

}
