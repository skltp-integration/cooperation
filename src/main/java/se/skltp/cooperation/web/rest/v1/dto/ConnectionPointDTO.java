package se.skltp.cooperation.web.rest.v1.dto;

import java.util.Date;

import se.skltp.cooperation.web.rest.v1.config.JsonDateSerializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A ConnectionPoint Data Transfer Object
 *
 * @author Peter Merikan
 */
@JacksonXmlRootElement(localName = "connectionPoint")
@JsonInclude(Include.NON_EMPTY)
public class ConnectionPointDTO {


	private Long id;
	private String platform;
	private String environment;
	@JsonSerialize(using=JsonDateSerializer.class)
	private Date snapshotTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public Date getSnapshotTime() {
		return snapshotTime;
	}

	public void setSnapshotTime(Date snapshotTime) {
		this.snapshotTime = snapshotTime;
	}
	

}
