/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.v2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * A Cooperation Data Transfer Object with associations
 *
 */

@JsonRootName("cooperation")
@JsonInclude(Include.NON_EMPTY)
public class CooperationDTO {
	private Long id;

	private ServiceConsumerDTO serviceConsumer;
	private LogicalAddressDTO logicalAddress;
	private ConnectionPointDTO connectionPoint;
	private ServiceContractDTO serviceContract;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceConsumerDTO getServiceConsumer() {
		return serviceConsumer;
	}

	public void setServiceConsumer(ServiceConsumerDTO serviceConsumer) {
		this.serviceConsumer = serviceConsumer;
	}

	public LogicalAddressDTO getLogicalAddress() {
		return logicalAddress;
	}

	public void setLogicalAddress(LogicalAddressDTO logicalAddress) {
		this.logicalAddress = logicalAddress;
	}

	public ConnectionPointDTO getConnectionPoint() {
		return connectionPoint;
	}

	public void setConnectionPoint(ConnectionPointDTO connectionPoint) {
		this.connectionPoint = connectionPoint;
	}

	public ServiceContractDTO getServiceContract() {
		return serviceContract;
	}

	public void setServiceContract(ServiceContractDTO serviceContract) {
		this.serviceContract = serviceContract;
	}
}
