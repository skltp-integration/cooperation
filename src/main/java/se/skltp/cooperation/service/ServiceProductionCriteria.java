package se.skltp.cooperation.service;

/**
 * A criteria object to be used when fetching Cooperations
 *
 * @author Jan Vasternas
 */
public class ServiceProductionCriteria {

	String physicalAddress;
	String rivtaProfile;
	Long serviceProducerId;
	Long logicalAddressId;
	Long serviceContractId;
	Long connectionPointId;

	public ServiceProductionCriteria() {
	}

	public ServiceProductionCriteria(String physicalAddress, String rivtaProfile,
			Long serviceProducerId, Long logicalAddressId, Long serviceContractId,
			Long connectionPointId) {
		this.physicalAddress = physicalAddress;
		this.rivtaProfile = rivtaProfile;
		this.serviceProducerId = serviceProducerId;
		this.logicalAddressId = logicalAddressId;
		this.serviceContractId = serviceContractId;
		this.connectionPointId = connectionPointId;
	}

	public boolean isEmpty() {
		return physicalAddress == null && rivtaProfile == null && serviceProducerId == null
				&& logicalAddressId == null && serviceContractId == null && connectionPointId == null;
	}

	public Long getServiceProducerId() {
		return serviceProducerId;
	}

	public void setServiceProducerId(Long serviceProducerId) {
		this.serviceProducerId = serviceProducerId;
	}

	public Long getLogicalAddressId() {
		return logicalAddressId;
	}

	public void setLogicalAddressId(Long logicalAddressId) {
		this.logicalAddressId = logicalAddressId;
	}

	public Long getServiceContractId() {
		return serviceContractId;
	}

	public void setServiceContractId(Long serviceContractId) {
		this.serviceContractId = serviceContractId;
	}

	public Long getConnectionPointId() {
		return connectionPointId;
	}

	public void setConnectionPointId(Long connectionPointId) {
		this.connectionPointId = connectionPointId;
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
