package se.skltp.cooperation.web.rest.v1.listdto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import se.skltp.cooperation.web.rest.v1.dto.ServiceProductionDTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A wrapper object to hold a list of {@link se.skltp.cooperation.web.rest.v1.dto.ServiceProductionDTO} objects.
 *
 * @author Peter Merikan
 */
@JacksonXmlRootElement(localName="serviceProductions")
public class ServiceProductionListDTO {

	@JacksonXmlProperty(localName = "serviceProduction")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ServiceProductionDTO> serviceProductions = new ArrayList<>();

	@XmlElement(name = "serviceProduction")
	public List<ServiceProductionDTO> getServiceProductions() {
		return serviceProductions;
	}

	public void setServiceProductions(List<ServiceProductionDTO> serviceProductions) {
		this.serviceProductions = serviceProductions;
	}
	
}
