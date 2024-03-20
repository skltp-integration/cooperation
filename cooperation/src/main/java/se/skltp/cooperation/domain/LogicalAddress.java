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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A LogicalAddress.
 *
 * @author Peter Merikan
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
