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
 * A LogicalAddress.
 *
 */
@Entity
@Table(name = "LOGICALADDRESS")
public class LogicalAddress implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "logical_address", unique = true)
	private String logicalAddress;

	@Column(name = "description")
	private String description;

	@OneToMany(mappedBy = "logicalAddress")
	private Set<Cooperation> cooperations = new HashSet<>();

	@OneToMany(mappedBy = "logicalAddress")
	private Set<ServiceProduction> serviceProductions = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogicalAddress() {
		return logicalAddress;
	}

	public void setLogicalAddress(String logicalAddress) {
		this.logicalAddress = logicalAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

}
