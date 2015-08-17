package se.skltp.cooperation.web.rest.v1.serviceConsumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import se.skltp.cooperation.web.rest.v1.dto.ServiceConsumerBaseDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * A list object for serviceConsumers
 *
 * @author Peter Merikan
 */
@JacksonXmlRootElement(localName="serviceConsumers")
public class ServiceConsumerListDTO {

	@JacksonXmlProperty(localName = "serviceConsumer")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ServiceConsumerBaseDTO> serviceConsumers = new ArrayList<>();

	public List<ServiceConsumerBaseDTO> getServiceConsumers() {
		return serviceConsumers;
	}

	public void setServiceConsumers(List<ServiceConsumerBaseDTO> serviceConsumers) {
		this.serviceConsumers = serviceConsumers;
	}
}
