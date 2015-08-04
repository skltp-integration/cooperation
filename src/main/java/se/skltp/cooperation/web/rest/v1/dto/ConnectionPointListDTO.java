package se.skltp.cooperation.web.rest.v1.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * A connectionPoints with contectionpoints
 *
 * @author Peter Merikan
 */
@XmlRootElement(name = "connectionPoints")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConnectionPointListDTO {

	@XmlElement(name = "connectionPoint")
	private List<ConnectionPointDTO> connectionPoints = new ArrayList<>();

	public List<ConnectionPointDTO> getConnectionPoints() {
		return connectionPoints;
	}

	public void setConnectionPoints(List<ConnectionPointDTO> connectionPoints) {
		this.connectionPoints = connectionPoints;
	}
}
