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

	@OneToMany(mappedBy = "connectionPoint")
	private Set<ServiceProduction> serviceProductions = new HashSet<>();

	@OneToMany(mappedBy = "connectionPoint")
	private Set<Cooperation> cooperations = new HashSet<>();

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

}
