package se.skltp.cooperation.web.rest.v1.listdto;

import java.util.ArrayList;
import java.util.List;

import se.skltp.cooperation.web.rest.v1.dto.ServiceConsumerDTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A wrapper object to hold a list of {@link se.skltp.cooperation.web.rest.v1.dto.ServiceConsumerDTO} objects.
 *
 * @author Peter Merikan
 */
@JacksonXmlRootElement(localName="serviceConsumers")
public class ServiceConsumerListDTO {

	@JacksonXmlProperty(localName = "serviceConsumer")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ServiceConsumerDTO> serviceConsumers = new ArrayList<>();

	public List<ServiceConsumerDTO> getServiceConsumers() {
		return serviceConsumers;
	}

	public void setServiceConsumers(List<ServiceConsumerDTO> serviceConsumers) {
		this.serviceConsumers = serviceConsumers;
	}
}
