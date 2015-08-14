package se.skltp.cooperation.web.rest.v1.serviceConsumer;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import se.skltp.cooperation.web.rest.v1.dto.CooperationBaseDTO;
import se.skltp.cooperation.web.rest.v1.dto.ServiceConsumerBaseDTO;

import java.util.HashSet;
import java.util.Set;

/**
 * A ServiceConsumer Data Transfer Object with associations
 *
 * @author Peter Merikan
 */
@JacksonXmlRootElement(localName = "serviceConsumer")
public class ServiceConsumerDTO extends ServiceConsumerBaseDTO {

	@JsonManagedReference
	@JacksonXmlElementWrapper(localName = "cooperations")
	@JacksonXmlProperty(localName = "cooperation")
	private Set<CooperationBaseDTO> cooperations = new HashSet<>();

	public Set<CooperationBaseDTO> getCooperations() {
		return cooperations;
	}

	public void setCooperations(Set<CooperationBaseDTO> cooperations) {
		this.cooperations = cooperations;
	}

}
