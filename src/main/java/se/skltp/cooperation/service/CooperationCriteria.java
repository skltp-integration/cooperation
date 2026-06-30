/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
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
