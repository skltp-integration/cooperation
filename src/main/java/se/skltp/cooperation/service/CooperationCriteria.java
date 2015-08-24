package se.skltp.cooperation.service;

/**
 * A criteria object to be used when fetching Cooperations
 *
 * @author Peter Merikan
 */
public class CooperationCriteria {

	Long serviceConsumerId;
	Long logicalAddressId;
	Long serviceContractId;
	Long connectionPointId;

	
	public CooperationCriteria() {
	}

	public CooperationCriteria(Long serviceConsumerId, Long logicalAddressId,
			Long serviceContractId, Long connectionPointId) {
		this.serviceConsumerId = serviceConsumerId;
		this.logicalAddressId = logicalAddressId;
		this.serviceContractId = serviceContractId;
		this.connectionPointId = connectionPointId;
	}

	public boolean isEmpty() {

		return serviceConsumerId == null && logicalAddressId == null
			&& serviceContractId == null && connectionPointId == null;
	}

	public Long getServiceConsumerId() {
		return serviceConsumerId;
	}

	public void setServiceConsumerId(Long serviceConsumerId) {
		this.serviceConsumerId = serviceConsumerId;
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
}
