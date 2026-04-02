/**
 * Copyright (c) 2015-2026 Inera.
 * * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.service;


public class ServiceContractCriteria {

	String namespace;
	Long serviceConsumerId;
	Long logicalAddressId;
	Long connectionPointId;
	Long serviceProducerId;
	Long serviceDomainId;

	public ServiceContractCriteria(String namespace, Long serviceConsumerId,
			Long logicalAddressId, Long connectionPointId, Long serviceProducerId, Long serviceDomainId) {
		this.namespace = namespace;
		this.serviceConsumerId = serviceConsumerId;
		this.logicalAddressId = logicalAddressId;
		this.connectionPointId = connectionPointId;
		this.serviceProducerId = serviceProducerId;
		this.serviceDomainId = serviceDomainId;
	}

	public boolean isEmpty() {

		return namespace == null && serviceConsumerId == null && logicalAddressId == null
			&& connectionPointId == null && serviceProducerId == null && serviceDomainId == null;
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

	public Long getServiceDomainId() {
		return serviceDomainId;
	}

	public void setServiceDomainId(Long serviceDomainId) {
		this.serviceDomainId = serviceDomainId;
	}

}
