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
 * A ServiceContract.
 *
 * @author Peter Merikan
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

}
