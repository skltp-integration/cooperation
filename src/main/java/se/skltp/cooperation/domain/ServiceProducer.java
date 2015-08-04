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
 * A ServiceProducer.
 *
 * @author Peter Merikan
 */
@Entity
@Table(name = "SERVICEPRODUCER")
public class ServiceProducer implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "description")
	private String description;

	@Column(name = "hsa_id", unique = true)
	private String hsaId;

	@OneToMany(mappedBy = "serviceProducer")
	private Set<ServiceProduction> serviceProductions = new HashSet<>();

	public String getHsaId() {
		return hsaId;
	}

	public void setHsaId(String hsaId) {
		this.hsaId = hsaId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<ServiceProduction> getServiceProductions() {
		return serviceProductions;
	}

	public void setServiceProductions(Set<ServiceProduction> serviceProductions) {
		this.serviceProductions = serviceProductions;
	}

}
