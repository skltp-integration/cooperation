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
 * {@link se.skltp.cooperation.domain.ServiceProducer}
 *
 * @author Jan Vasternas
 */
public class ServiceProducerCriteria {
	private String hsaId;
	private Long connectionPointId;
	private Long logicalAddressId;
	private Long serviceContractId;
	private Long serviceConsumerId;

	public ServiceProducerCriteria(String hsaId, Long connectionPointId, Long logicalAddressId,
			Long serviceContractId, Long serviceConsumerId) {
		this.hsaId = hsaId;
		this.connectionPointId = connectionPointId;
		this.logicalAddressId = logicalAddressId;
		this.serviceContractId = serviceContractId;
		this.serviceConsumerId = serviceConsumerId;
	}

	public boolean isEmpty() {
		return hsaId == null && connectionPointId == null && logicalAddressId == null
				&& serviceContractId == null && serviceConsumerId == null;
	}

	public String getHsaId() {
		return hsaId;
	}

	public void setHsaId(String hsaId) {
		this.hsaId = hsaId;
	}

	public void setServiceContractId(Long serviceContractId) {
		this.serviceContractId = serviceContractId;
	}

	public ServiceProducerCriteria() {
	}

	public Long getConnectionPointId() {
		return connectionPointId;
	}

	public void setConnectionPointId(Long connectionPointId) {
		this.connectionPointId = connectionPointId;
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

	public void setServiceContractIdId(Long serviceContractId) {
		this.serviceContractId = serviceContractId;
	}

	public Long getServiceConsumerId() {
		return serviceConsumerId;
	}

	public void setServiceConsumerId(Long serviceConsumerId) {
		this.serviceConsumerId = serviceConsumerId;
	}

}
