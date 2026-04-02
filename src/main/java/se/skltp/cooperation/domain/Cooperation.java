/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Cooperation.
 *
 */
@Entity
@Table(name = "COOPERATION")
@NamedEntityGraph(name = "Cooperation.eager",
	attributeNodes = { @NamedAttributeNode("serviceConsumer"), @NamedAttributeNode("logicalAddress"), @NamedAttributeNode("serviceContract") }
)
public class Cooperation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private ServiceConsumer serviceConsumer;

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

	public ServiceConsumer getServiceConsumer() {
		return serviceConsumer;
	}

	public void setServiceConsumer(ServiceConsumer serviceConsumer) {
		this.serviceConsumer = serviceConsumer;
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
