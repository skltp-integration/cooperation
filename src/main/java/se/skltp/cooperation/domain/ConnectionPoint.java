/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

/**
 * A ConnectionPoint.
 *
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
