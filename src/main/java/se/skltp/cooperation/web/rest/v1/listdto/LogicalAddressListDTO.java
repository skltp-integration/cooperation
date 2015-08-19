package se.skltp.cooperation.web.rest.v1.listdto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import se.skltp.cooperation.web.rest.v1.dto.ConnectionPointDTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A wrapper object to hold a list of {@link se.skltp.cooperation.web.rest.v1.dto.LogicalAddressDTO} objects.
 *
 * @author Jan Vasternas
 */
@JacksonXmlRootElement(localName="logicalAddresss")
public class LogicalAddressListDTO {

	@JacksonXmlProperty(localName = "logicalAddress")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ConnectionPointDTO> logicalAddresss = new ArrayList<>();

	@XmlElement(name = "logicalAddress")
	public List<ConnectionPointDTO> getLogicalAddresss() {
		return logicalAddresss;
	}

	public void setLogicalAddresss(List<ConnectionPointDTO> logicalAddresss) {
		this.logicalAddresss = logicalAddresss;
	}
	
}
