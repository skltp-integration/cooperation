package se.skltp.cooperation.web.rest.v1.listdto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import se.skltp.cooperation.web.rest.v1.dto.ConnectionPointDTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A wrapper object to hold a list of {@link se.skltp.cooperation.web.rest.v1.dto.ConnectionPointDTO} objects.
 *
 * @author Peter Merikan
 */
@JacksonXmlRootElement(localName="connectionPoints")
public class ConnectionPointListDTO {

	@JacksonXmlProperty(localName = "connectionPoint")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ConnectionPointDTO> connectionPoints = new ArrayList<>();

	@XmlElement(name = "connectionPoint")
	public List<ConnectionPointDTO> getConnectionPoints() {
		return connectionPoints;
	}

	public void setConnectionPoints(List<ConnectionPointDTO> connectionPoints) {
		this.connectionPoints = connectionPoints;
	}
}
