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

import se.skltp.cooperation.api.v2.dto.ConnectionPointDTO;

import com.fasterxml.jackson.annotation.JsonRootName;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * A wrapper object to hold a list of {@link ConnectionPointDTO} objects.
 *
 */
@JsonRootName("connectionPoints")
public class ConnectionPointListDTO {

	@JacksonXmlProperty(localName = "connectionPoint")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ConnectionPointDTO> connectionPoints = new ArrayList<>();

	public ConnectionPointListDTO(List<ConnectionPointDTO> connectionPoints) {
		super();
		this.connectionPoints = connectionPoints;
	}

	@XmlElement(name = "connectionPoint")
	public List<ConnectionPointDTO> getConnectionPoints() {
		return connectionPoints;
	}

	public void setConnectionPoints(List<ConnectionPointDTO> connectionPoints) {
		this.connectionPoints = connectionPoints;
	}
}
