package se.skltp.cooperation.service;


public class ServiceContractCriteria {

	String namespace;
	Long serviceConsumerId;
	Long logicalAddressId;
	Long connectionPointId;
	Long serviceProducerId;
	
	
	public ServiceContractCriteria(String namespace, Long serviceConsumerId,
			Long logicalAddressId, Long connectionPointId, Long serviceProducerId) {
		this.namespace = namespace;
		this.serviceConsumerId = serviceConsumerId;
		this.logicalAddressId = logicalAddressId;
		this.connectionPointId = connectionPointId;
		this.serviceProducerId = serviceProducerId;
	}

	public boolean isEmpty() {

		return namespace == null && serviceConsumerId == null && logicalAddressId == null
			&& connectionPointId == null && serviceProducerId == null;
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

	public Long getServiceProducerId() {
		return serviceProducerId;
	}

	public void setServiceProducerId(Long serviceProducerId) {
		this.serviceProducerId = serviceProducerId;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public Long getConnectionPointId() {
		return connectionPointId;
	}

	public void setConnectionPointId(Long connectionPointId) {
		this.connectionPointId = connectionPointId;
	}

}
