/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.service;


public class InstalledContractCriteria {

	Long connectionPointId;
	Long serviceContractId;
	Long serviceDomainId;

	public InstalledContractCriteria(Long connectionPointId, Long serviceContractId, Long serviceDomainId) {
		this.connectionPointId = connectionPointId;
		this.serviceContractId = serviceContractId;
		this.serviceDomainId = serviceDomainId;
	}

	public boolean isEmpty() {

		return connectionPointId == null && serviceContractId == null && serviceDomainId == null;
	}

	public Long getConnectionPointId() {
		return connectionPointId;
	}

	public void setConnectionPointId(Long connectionPointId) {
		this.connectionPointId = connectionPointId;
	}

	public Long getServiceContractId() {
		return serviceContractId;
	}

	public void setServiceContractId(Long serviceContractId) {
		this.serviceContractId = serviceContractId;
	}

	public Long getServiceDomainId() {
		return serviceDomainId;
	}

	public void setServiceDomainId(Long serviceDomainId) {
		this.serviceDomainId = serviceDomainId;
	}

}
