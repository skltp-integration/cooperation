/**
 * Copyright (c) 2015-2026 Inera.
 * * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.service;

/**
 * A criteria object to be used when fetching
 * {@link se.skltp.cooperation.domain.ServiceConsumer}
 *
 */
public class ServiceConsumerCriteria {
	private Long connectionPointId;
	private Long logicalAddressId;
	private Long serviceContractId;
	private Long serviceProducerId;

	public ServiceConsumerCriteria() {
	}

	public ServiceConsumerCriteria(Long connectionPointId, Long logicalAddressId,
			Long serviceContractId, Long serviceProducerId) {
		this.connectionPointId = connectionPointId;
		this.logicalAddressId = logicalAddressId;
		this.serviceContractId = serviceContractId;
		this.serviceProducerId = serviceProducerId;
	}

	public Long getConnectionPointId() {
		return connectionPointId;
	}

	public void setConnectionPointId(Long connectionPointId) {
		this.connectionPointId = connectionPointId;
	}

	public boolean isEmpty() {
		return connectionPointId == null && logicalAddressId == null && serviceContractId == null
				&& serviceProducerId == null;
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
