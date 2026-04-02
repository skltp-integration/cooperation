/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ServiceContract.
 *
 */
@Entity
@Table(name = "SERVICECONTRACT")
public class ServiceContract implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "namespace")
	private String namespace;

	@Column(name = "major")
	private Integer major;

	@Column(name = "minor")
	private Integer minor;

	@OneToMany(mappedBy = "serviceContract")
	private Set<Cooperation> cooperations = new HashSet<>();

	@OneToMany(mappedBy = "serviceContract")
	private Set<ServiceProduction> serviceProductions = new HashSet<>();

	@OneToMany(mappedBy = "serviceContract")
	private Set<InstalledContract> installedContracts = new HashSet<>();

	@ManyToOne
	private ServiceDomain serviceDomain;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public Integer getMajor() {
		return major;
	}

	public void setMajor(Integer major) {
		this.major = major;
	}

	public Integer getMinor() {
		return minor;
	}

	public void setMinor(Integer minor) {
		this.minor = minor;
	}

	public Set<Cooperation> getCooperations() {
		return cooperations;
	}

	public void setCooperations(Set<Cooperation> cooperations) {
		this.cooperations = cooperations;
	}

	public Set<ServiceProduction> getServiceProductions() {
		return serviceProductions;
	}

	public void setServiceProductions(Set<ServiceProduction> serviceProductions) {
		this.serviceProductions = serviceProductions;
	}

	public ServiceDomain getServiceDomain() {
		return serviceDomain;
	}

	public void setServiceDomain(ServiceDomain serviceDomain) {
		this.serviceDomain = serviceDomain;
	}

	public Set<InstalledContract> getInstalledContracts() {
		return installedContracts;
	}

	public void setInstalledContracts(Set<InstalledContract> installedContracts) {
		this.installedContracts = installedContracts;
	}

}
