/**
 * Copyright (c) 2014 Center for eHalsa i samverkan (CeHis).
 * 								<http://cehis.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package se.skltp.cooperation.service;

/**
 * A criteria object to be used when fetching
 * {@link se.skltp.cooperation.domain.ServiceConsumer}
 *
 * @author Peter Merikan
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
