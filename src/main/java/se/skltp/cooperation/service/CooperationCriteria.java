/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.service;

/**
 * A criteria object to be used when fetching Cooperations
 *
 */
public class CooperationCriteria {

	Long serviceConsumerId;
	Long logicalAddressId;
	Long serviceContractId;
	Long connectionPointId;
	Long serviceDomainId;

	public CooperationCriteria() {
	}

	public CooperationCriteria(Long serviceConsumerId, Long logicalAddressId,
			Long serviceContractId, Long connectionPointId, Long serviceDomainId) {
		this.serviceConsumerId = serviceConsumerId;
		this.logicalAddressId = logicalAddressId;
		this.serviceContractId = serviceContractId;
		this.connectionPointId = connectionPointId;
		this.serviceDomainId = serviceDomainId;
	}

	public boolean isEmpty() {

		return serviceConsumerId == null && logicalAddressId == null && serviceContractId == null
				&& connectionPointId == null && serviceDomainId == null;
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

	public Long getServiceDomainId() {
		return serviceDomainId;
	}

	public void setServiceDomainId(Long serviceDomainId) {
		this.serviceDomainId = serviceDomainId;
	}


}
