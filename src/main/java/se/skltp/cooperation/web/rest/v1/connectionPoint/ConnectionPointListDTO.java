package se.skltp.cooperation.web.rest.v1.connectionPoint;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import se.skltp.cooperation.web.rest.v1.dto.ConnectionPointBaseDTO;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * A connectionPoints with contectionpoints
 *
 * @author Peter Merikan
 */
@JacksonXmlRootElement(localName="connectionPoints")
public class ConnectionPointListDTO {

	@JacksonXmlProperty(localName = "connectionPoint")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ConnectionPointBaseDTO> connectionPoints = new ArrayList<>();

	@XmlElement(name = "connectionPoint")
	public List<ConnectionPointBaseDTO> getConnectionPoints() {
		return connectionPoints;
	}

	public void setConnectionPoints(List<ConnectionPointBaseDTO> connectionPoints) {
		this.connectionPoints = connectionPoints;
	}
}
