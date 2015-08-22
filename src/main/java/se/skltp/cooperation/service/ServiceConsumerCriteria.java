package se.skltp.cooperation.service;

/**
 * A criteria object to be used when fetching
 * {@link se.skltp.cooperation.domain.ServiceConsumer}
 *
 * @author Peter Merikan
 */
public class ServiceConsumerCriteria {
	private Long connectionPointId;
	private Long logicalAddressId;
	private Long serviceContractId;

	
	
	public ServiceConsumerCriteria() {
	}

	public ServiceConsumerCriteria(Long connectionPointId, Long logicalAddressId,
			Long serviceContractId) {
		this.connectionPointId = connectionPointId;
		this.logicalAddressId = logicalAddressId;
		this.serviceContractId = serviceContractId;
	}

	public Long getConnectionPointId() {
		return connectionPointId;
	}

	public void setConnectionPointId(Long connectionPointId) {
		this.connectionPointId = connectionPointId;
	}

	public boolean isEmpty() {
		return (connectionPointId == null);
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

}
