/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A ServiceProduction.
 *
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
