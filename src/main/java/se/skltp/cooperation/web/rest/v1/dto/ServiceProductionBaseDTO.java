package se.skltp.cooperation.web.rest.v1.dto;

/**
 * A base ServiceProduction Data Transfer Object without associations
 *
 * @author Peter Merikan
 */
public class ServiceProductionBaseDTO {
	private Long id;
	private String physicalAddress;
	private String rivtaProfile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhysicalAddress() {
		return physicalAddress;
	}

	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

	public String getRivtaProfile() {
		return rivtaProfile;
	}

	public void setRivtaProfile(String rivtaProfile) {
		this.rivtaProfile = rivtaProfile;
	}

}
