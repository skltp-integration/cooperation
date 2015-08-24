package se.skltp.cooperation.service;


public class ConnectionPointCriteria {

	String environment;
	String platform;
	Long serviceConsumerId;
	Long logicalAddressId;
	Long serviceContractId;
	Long serviceProducerId;
	
	public boolean isEmpty() {

		return environment == null && platform == null && serviceConsumerId == null && logicalAddressId == null
			&& serviceContractId == null && serviceProducerId == null;
	}
	
	public ConnectionPointCriteria(String environment, String platform, Long serviceConsumerId,
			Long logicalAddressId, Long serviceContractId, Long serviceProducerId) {
		super();
		this.environment = environment;
		this.platform = platform;
		this.serviceConsumerId = serviceConsumerId;
		this.logicalAddressId = logicalAddressId;
		this.serviceContractId = serviceContractId;
		this.serviceProducerId = serviceProducerId;
	}


	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
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

	public Long getServiceProducerId() {
		return serviceProducerId;
	}

	public void setServiceProducerId(Long serviceProducerId) {
		this.serviceProducerId = serviceProducerId;
	}

}
