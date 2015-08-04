package se.skltp.cooperation.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A ServiceProduction.
 *
 * @author Peter Merikan
 */
@Entity
@Table(name = "SERVICEPRODUCTION")
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
