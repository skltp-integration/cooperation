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

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * A ConnectionPoint.
 *
 * @author Peter Merikan
 */
@Entity
@Table(name = "CONNECTIONPOINT")
public class ConnectionPoint implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "platform")
	private String platform;

	@Column(name = "environment")
	private String environment;

	@Column(name = "snapshot_time")
	private Date snapshotTime;

	@OneToMany(mappedBy = "connectionPoint")
	private Set<ServiceProduction> serviceProductions = new HashSet<>();

	@OneToMany(mappedBy = "connectionPoint")
	private Set<Cooperation> cooperations = new HashSet<>();

	@OneToMany(mappedBy = "connectionPoint")
	private Set<InstalledContract> installedContracts = new HashSet<>();

	@OneToMany(mappedBy = "connectionPoint")
	private Set<ServiceConsumer> serviceConsumers = new HashSet<>();

	@OneToMany(mappedBy = "connectionPoint")
	private Set<ServiceProducer> serviceProducers = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public Set<ServiceProduction> getServiceProductions() {
		return serviceProductions;
	}

	public void setServiceProductions(Set<ServiceProduction> serviceProductions) {
		this.serviceProductions = serviceProductions;
	}

	public Set<Cooperation> getCooperations() {
		return cooperations;
	}

	public void setCooperations(Set<Cooperation> cooperations) {
		this.cooperations = cooperations;
	}

	public Date getSnapshotTime() {
		return snapshotTime;
	}

	public void setSnapshotTime(Date snapshotTime) {
		this.snapshotTime = snapshotTime;
	}

	public Set<InstalledContract> getInstalledContracts() {
		return installedContracts;
	}

	public void setInstalledContracts(Set<InstalledContract> installedContracts) {
		this.installedContracts = installedContracts;
	}

	public Set<ServiceConsumer> getServiceConsumers() {
		return serviceConsumers;
	}

	public void setServiceConsumers(Set<ServiceConsumer> serviceConsumers) {
		this.serviceConsumers = serviceConsumers;
	}

	public Set<ServiceProducer> getServiceProducers() {
		return serviceProducers;
	}

	public void setServiceProducers(Set<ServiceProducer> serviceProducers) {
		this.serviceProducers = serviceProducers;
	}
	

}
