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
 */
@Entity
@Table(name = "LOGICALADDRESS")
public class LogicalAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
