package se.skltp.cooperation.web.rest.v1.listdto;

import java.util.ArrayList;
import java.util.List;

import se.skltp.cooperation.web.rest.v1.dto.ServiceProducerDTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A wrapper object to hold a list of {@link se.skltp.cooperation.web.rest.v1.dto.ServiceProducerDTO} objects.
 *
 * @author Peter Merikan
 */
@JacksonXmlRootElement(localName="serviceProducers")
public class ServiceProducerListDTO {

	@JacksonXmlProperty(localName = "serviceProducer")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ServiceProducerDTO> serviceProducers = new ArrayList<>();

	public List<ServiceProducerDTO> getServiceProducers() {
		return serviceProducers;
	}

	public void setServiceProducers(List<ServiceProducerDTO> serviceProducers) {
		this.serviceProducers = serviceProducers;
	}
}
