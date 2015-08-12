package se.skltp.cooperation.web.rest.v1.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.HashSet;
import java.util.Set;

/**
 * A ServiceConsumer Data Transfer Object
 *
 * @author Peter Merikan
 */
@JacksonXmlRootElement(localName = "serviceConsumer")
public class ServiceConsumerDTO {

	private Long id;
	private String description;
	private String hsaId;
	@JsonManagedReference
	@JacksonXmlElementWrapper(localName = "cooperations")
	@JacksonXmlProperty(localName = "cooperation")
	private Set<CooperationDTO> cooperations = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHsaId() {
		return hsaId;
	}

	public void setHsaId(String hsaId) {
		this.hsaId = hsaId;
	}

	public Set<CooperationDTO> getCooperations() {
		return cooperations;
	}

	public void setCooperations(Set<CooperationDTO> cooperations) {
		this.cooperations = cooperations;
	}

}
