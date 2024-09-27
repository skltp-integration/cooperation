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
package se.skltp.cooperation.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A ServiceProduction.
 *
 * @author Peter Merikan
 */
@Entity
@Table(name = "SERVICEPRODUCTION")
@NamedEntityGraph(name = "ServiceProduction.eager",
	attributeNodes = { @NamedAttributeNode("serviceProducer"), @NamedAttributeNode("logicalAddress"), @NamedAttributeNode("serviceContract") }
)
public class ServiceProduction implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "physical_address")
	private String physicalAddress;

	@Column(name = "rivta_profile")
	private String rivtaProfile;

	@ManyToOne
	private ServiceProducer serviceProducer;

	@ManyToOne
	private LogicalAddress logicalAddress;

	@ManyToOne
	private ConnectionPoint connectionPoint;

	@ManyToOne
	private ServiceContract serviceContract;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public ServiceProducer getServiceProducer() {
		return serviceProducer;
	}

	public void setServiceProducer(ServiceProducer serviceProducer) {
		this.serviceProducer = serviceProducer;
	}

	public LogicalAddress getLogicalAddress() {
		return logicalAddress;
	}

	public void setLogicalAddress(LogicalAddress logicalAddress) {
		this.logicalAddress = logicalAddress;
	}

	public ConnectionPoint getConnectionPoint() {
		return connectionPoint;
	}

	public void setConnectionPoint(ConnectionPoint connectionPoint) {
		this.connectionPoint = connectionPoint;
	}

	public ServiceContract getServiceContract() {
		return serviceContract;
	}

	public void setServiceContract(ServiceContract serviceContract) {
		this.serviceContract = serviceContract;
	}

}
