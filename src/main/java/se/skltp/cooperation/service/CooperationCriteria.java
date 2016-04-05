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
 * A criteria object to be used when fetching Cooperations
 *
 * @author Peter Merikan
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
