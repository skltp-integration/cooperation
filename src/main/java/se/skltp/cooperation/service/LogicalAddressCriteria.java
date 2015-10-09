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


public class LogicalAddressCriteria {

	String namespace;
	Long serviceConsumerId;
	Long serviceContractId;
	Long connectionPointId;
	Long serviceProducerId;
	
	public boolean isEmpty() {

		return namespace == null && serviceConsumerId == null && serviceContractId == null
			&& connectionPointId == null && serviceProducerId == null;
	}
	
	public LogicalAddressCriteria(String namespace, Long serviceConsumerId,
			Long serviceContractId, Long connectionPointId, Long serviceProducerId) {
		this.namespace = namespace;
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

}
