package se.skltp.cooperation.web.rest.v1.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A base Cooperation Data Transfer Object without associations
 *
 * @author Peter Merikan
 */

@JacksonXmlRootElement(localName = "cooperation")
public class CooperationBaseDTO {
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
