/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

/**
 * A ServiceContract.
 *
 * @author Jan Vasternas
 */
@Entity
@Table(name = "SERVICEDOMAIN")
public class ServiceDomain implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "namespace")
	private String namespace;

	@OneToMany(mappedBy = "serviceDomain")
	private Set<ServiceContract> serviceContracts = new HashSet<>();

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

	public Set<ServiceContract> getServiceContracts() {
		return serviceContracts;
	}

	public void setServiceContracts(Set<ServiceContract> serviceContracts) {
		this.serviceContracts = serviceContracts;
	}

}
