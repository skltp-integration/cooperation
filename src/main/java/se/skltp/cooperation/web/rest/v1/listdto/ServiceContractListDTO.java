package se.skltp.cooperation.web.rest.v1.listdto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import se.skltp.cooperation.web.rest.v1.dto.ServiceContractDTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A wrapper object to hold a list of {@link se.skltp.cooperation.web.rest.v1.dto.ServiceContractDTO} objects.
 *
 * @author Jan Vasternas
 */
@JacksonXmlRootElement(localName="serviceContracts")
public class ServiceContractListDTO {

	@JacksonXmlProperty(localName = "serviceContract")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ServiceContractDTO> serviceContracts = new ArrayList<>();

	@XmlElement(name = "serviceContract")
	public List<ServiceContractDTO> getServiceContracts() {
		return serviceContracts;
	}

	public void setServiceContracts(List<ServiceContractDTO> serviceContracts) {
		this.serviceContracts = serviceContracts;
	}
}
