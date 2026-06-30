/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
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
