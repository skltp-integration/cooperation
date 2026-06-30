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
 * @author Jan Vasternas
 */
public class ServiceProductionCriteria {

	String physicalAddress;
	String rivtaProfile;
	Long serviceProducerId;
	Long logicalAddressId;
	Long serviceContractId;
	Long connectionPointId;
	Long domainId;

	public ServiceProductionCriteria() {
	}

	public ServiceProductionCriteria(String physicalAddress, String rivtaProfile,
			Long serviceProducerId, Long logicalAddressId, Long serviceContractId,
			Long connectionPointId, Long domainId) {
		this.physicalAddress = physicalAddress;
		this.rivtaProfile = rivtaProfile;
		this.serviceProducerId = serviceProducerId;
		this.logicalAddressId = logicalAddressId;
		this.serviceContractId = serviceContractId;
		this.connectionPointId = connectionPointId;
		this.domainId = domainId;
	}

	public boolean isEmpty() {
		return physicalAddress == null && rivtaProfile == null && serviceProducerId == null
				&& logicalAddressId == null && serviceContractId == null && connectionPointId == null && domainId == null;
	}

	public Long getServiceProducerId() {
		return serviceProducerId;
	}

	public void setServiceProducerId(Long serviceProducerId) {
		this.serviceProducerId = serviceProducerId;
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

	public String getPhysicalAddress() {
		return physicalAddress;
	}

	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

	public String getRivtaProfile() {
		return rivtaProfile;
	}

	public void setRivtaProfile(String rivtaProfile) {
		this.rivtaProfile = rivtaProfile;
	}

	public Long getDomainId() {
		return domainId;
	}

	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

}
