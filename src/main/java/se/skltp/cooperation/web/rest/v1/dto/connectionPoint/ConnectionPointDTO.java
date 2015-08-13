package se.skltp.cooperation.web.rest.v1.dto.connectionPoint;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import se.skltp.cooperation.web.rest.v1.dto.ConnectionPointBaseDTO;
import se.skltp.cooperation.web.rest.v1.dto.CooperationBaseDTO;
import se.skltp.cooperation.web.rest.v1.dto.ServiceProductionBaseDTO;

import java.util.HashSet;
import java.util.Set;

/**
 * A ConnectionPoint Data Transfer Object with associations
 *
 * @author Peter Merikan
 */
@JacksonXmlRootElement(localName = "connectionPoint")
public class ConnectionPointDTO extends ConnectionPointBaseDTO {


	@JsonManagedReference
	@JacksonXmlElementWrapper(localName = "serviceProductions")
	@JacksonXmlProperty(localName = "serviceProduction")
	private Set<ServiceProductionBaseDTO> serviceProductions = new HashSet<>();
	@JsonManagedReference
	@JacksonXmlElementWrapper(localName = "cooperations")
	@JacksonXmlProperty(localName = "cooperation")
	private Set<CooperationBaseDTO> cooperations = new HashSet<>();

	public Set<ServiceProductionBaseDTO> getServiceProductions() {
		return serviceProductions;
	}

	public void setServiceProductions(Set<ServiceProductionBaseDTO> serviceProductions) {
		this.serviceProductions = serviceProductions;
	}

	public Set<CooperationBaseDTO> getCooperations() {
		return cooperations;
	}

	public void setCooperations(Set<CooperationBaseDTO> cooperations) {
		this.cooperations = cooperations;
	}
}
