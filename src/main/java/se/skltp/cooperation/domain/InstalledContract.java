/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.domain;

import java.io.Serializable;

import jakarta.persistence.*;

/**
 * A Cooperation.
 *
 * @author Jan Västernäs
 */
@Entity
@Table(name = "INSTALLEDCONTRACT")
public class InstalledContract implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private ServiceContract serviceContract;

	@ManyToOne
	private ConnectionPoint connectionPoint;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
