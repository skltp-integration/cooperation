package se.skltp.cooperation.web.rest.v1.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A base ConnectionPoint Data Transfer Object without any associations
 *
 * @author Peter Merikan
 */
@JacksonXmlRootElement(localName = "connectionPoint")
public class ConnectionPointBaseDTO {


	private Long id;
	private String platform;
	private String environment;
//	@JsonManagedReference
//	@JacksonXmlElementWrapper(localName = "serviceProductions")
//	@JacksonXmlProperty(localName = "serviceProduction")
//	private Set<ServiceProductionDTO> serviceProductions = new HashSet<>();
//	@JsonManagedReference
//	@JacksonXmlElementWrapper(localName = "cooperations")
//	@JacksonXmlProperty(localName = "cooperation")
//	private Set<CooperationDTO> cooperations = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

//	public Set<ServiceProductionDTO> getServiceProductions() {
//		return serviceProductions;
//	}
//
//	public void setServiceProductions(Set<ServiceProductionDTO> serviceProductions) {
//		this.serviceProductions = serviceProductions;
//	}
//
//	public Set<CooperationDTO> getCooperations() {
//		return cooperations;
//	}
//
//	public void setCooperations(Set<CooperationDTO> cooperations) {
//		this.cooperations = cooperations;
//	}
}
