package se.skltp.cooperation.web.rest.v1.cooperation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A LogicalAddress Data Transfer Object
 *
 * @author Peter Merikan
 */
@JsonInclude(Include.NON_EMPTY)
public class LogicalAddressDTO {

	private Long id;
	private String description;
	private String logicalAddress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogicalAddress() {
		return logicalAddress;
	}

	public void setLogicalAddress(String logicalAddress) {
		this.logicalAddress = logicalAddress;
	}
}
