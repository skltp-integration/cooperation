/**
 * Copyright (c) 2015-2026 Inera.
 * * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.service;


public class LogicalAddressCriteria {

	String logicalAdress;
	Long serviceConsumerId;
	Long serviceContractId;
	Long connectionPointId;
	Long serviceProducerId;

	public boolean isEmpty() {

		return logicalAdress == null && serviceConsumerId == null && serviceContractId == null
			&& connectionPointId == null && serviceProducerId == null;
	}

	public LogicalAddressCriteria(String logicalAdress, Long serviceConsumerId,
			Long serviceContractId, Long connectionPointId, Long serviceProducerId) {
		this.logicalAdress = logicalAdress;
		this.serviceConsumerId = serviceConsumerId;
		this.serviceContractId = serviceContractId;
		this.connectionPointId = connectionPointId;
		this.serviceProducerId = serviceProducerId;
	}

	public Long getServiceConsumerId() {
		return serviceConsumerId;
	}

	public void setServiceConsumerId(Long serviceConsumerId) {
		this.serviceConsumerId = serviceConsumerId;
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

	public String getLogicalAdress() {
		return logicalAdress;
	}

	public void setLogicalAdress(String namespace) {
		this.logicalAdress = namespace;
	}

	public Long getConnectionPointId() {
		return connectionPointId;
	}

	public void setConnectionPointId(Long connectionPointId) {
		this.connectionPointId = connectionPointId;
	}

}
