package se.skltp.cooperation.service;


public class LogicalAddressCriteria {

	String namespace;
	Long serviceConsumerId;
	Long serviceContractId;
	Long connectionPointId;
	Long serviceProducerId;
	
	public boolean isEmpty() {

		return namespace == null && serviceConsumerId == null && serviceContractId == null
			&& connectionPointId == null && serviceProducerId == null;
	}
	
	public LogicalAddressCriteria(String namespace, Long serviceConsumerId,
			Long serviceContractId, Long connectionPointId, Long serviceProducerId) {
		this.namespace = namespace;
		this.serviceConsumerId = serviceConsumerId;
		this.serviceContractId = serviceContractId;
		this.connectionPointId = connectionPointId;
		this.serviceProducerId = serviceProducerId;
	}

	public Long getServiceConsumerId() {
		return serviceConsumerId;
	}

	public void setServiceConsumerId(Long serviceConsumerId) {
		this.serviceConsumerId = serviceConsumerId;
	}

	public Long getServiceContractId() {
		return serviceContractId;
	}

	public void setServiceContractId(Long serviceContractId) {
		this.serviceContractId = serviceContractId;
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
