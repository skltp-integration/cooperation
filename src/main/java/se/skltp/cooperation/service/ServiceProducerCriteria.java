package se.skltp.cooperation.service;


/**
 * A criteria object to be used when fetching
 * {@link se.skltp.cooperation.domain.ServiceProducer}
 *
 * @author Jan Vasternas
 */
public class ServiceProducerCriteria {
	private String hsaId;
	private Long connectionPointId;
	private Long logicalAddressId;
	private Long serviceContractId;


	public ServiceProducerCriteria(String hsaId,
			Long connectionPointId, Long logicalAddressId, Long serviceContractId) {
		this.hsaId = hsaId;
		this.connectionPointId = connectionPointId;
		this.logicalAddressId = logicalAddressId;
		this.serviceContractId = serviceContractId;
	}

	public boolean isEmpty() {
		return hsaId == null &&  connectionPointId == null
				&& logicalAddressId == null && serviceContractId == null;
	}

	public String getHsaId() {
		return hsaId;
	}

	public void setHsaId(String hsaId) {
		this.hsaId = hsaId;
	}

	public void setServiceContractId(Long serviceContractId) {
		this.serviceContractId = serviceContractId;
	}

	public ServiceProducerCriteria() {
	}

	public Long getConnectionPointId() {
		return connectionPointId;
	}

	public void setConnectionPointId(Long connectionPointId) {
		this.connectionPointId = connectionPointId;
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

	public void setServiceContractIdId(Long serviceContractId) {
		this.serviceContractId = serviceContractId;
	}
	
}
