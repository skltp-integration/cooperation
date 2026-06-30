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

import se.skltp.cooperation.api.v2.dto.InstalledContractDTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A wrapper object to hold a list of {@link InstalledContractDTO} objects.
 *
 * @author Jan Vasternas
 */
@JacksonXmlRootElement(localName="installedContracts")
public class InstalledContractListDTO {

	@JacksonXmlProperty(localName = "installedContract")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<InstalledContractDTO> installedContracts = new ArrayList<>();

	@XmlElement(name = "installedContract")
	public List<InstalledContractDTO> getInstalledContracts() {
		return installedContracts;
	}

	public void setInstalledContracts(List<InstalledContractDTO> installedContracts) {
		this.installedContracts = installedContracts;
	}


}
