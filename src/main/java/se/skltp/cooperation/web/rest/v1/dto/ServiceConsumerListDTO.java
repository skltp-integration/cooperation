package se.skltp.cooperation.web.rest.v1.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * A list object for serviceConsumers
 *
 * @author Peter Merikan
 */
@XmlRootElement(name = "serviceConsumers")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceConsumerListDTO {

	@XmlElement(name = "serviceConsumer")
	private List<ServiceConsumerDTO> serviceConsumers = new ArrayList<>();

	public List<ServiceConsumerDTO> getServiceConsumers() {
		return serviceConsumers;
	}

	public void setServiceConsumers(List<ServiceConsumerDTO> serviceConsumers) {
		this.serviceConsumers = serviceConsumers;
	}
}
