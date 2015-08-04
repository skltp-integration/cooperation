package se.skltp.cooperation.web.rest.v1.dto.cooperation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A LogicalAddress Data Transfer Object
 *
 * @author Peter Merikan
 */
@JsonInclude(Include.NON_NULL)
public class LogicalAddressDTO {

	private Long id;
	private String description;

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

}
