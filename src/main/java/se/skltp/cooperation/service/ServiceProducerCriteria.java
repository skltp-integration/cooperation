/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
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
	private Long serviceConsumerId;

	public ServiceProducerCriteria(String hsaId, Long connectionPointId, Long logicalAddressId,
			Long serviceContractId, Long serviceConsumerId) {
		this.hsaId = hsaId;
		this.connectionPointId = connectionPointId;
		this.logicalAddressId = logicalAddressId;
		this.serviceContractId = serviceContractId;
		this.serviceConsumerId = serviceConsumerId;
	}

	public boolean isEmpty() {
		return hsaId == null && connectionPointId == null && logicalAddressId == null
				&& serviceContractId == null && serviceConsumerId == null;
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

	public Long getServiceConsumerId() {
		return serviceConsumerId;
	}

	public void setServiceConsumerId(Long serviceConsumerId) {
		this.serviceConsumerId = serviceConsumerId;
	}

}
