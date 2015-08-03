package se.skltp.cooperation.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ServiceConsumer.
 *
 * @author Peter Merikan
 */
@Entity
@Table(name = "SERVICECONSUMER")
public class ServiceConsumer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "hsa_id", unique = true)
    private String hsaId;

    @OneToMany(mappedBy = "serviceConsumer")
    private Set<Cooperation> cooperations = new HashSet<>();

    @ManyToOne
    private ConnectionPoint connectionPoint;

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

    public String getHsaId() {
        return hsaId;
    }

    public void setHsaId(String hsaId) {
        this.hsaId = hsaId;
    }

    public Set<Cooperation> getCooperations() {
        return cooperations;
    }

    public void setCooperations(Set<Cooperation> cooperations) {
        this.cooperations = cooperations;
    }

    public ConnectionPoint getConnectionPoint() {
        return connectionPoint;
    }

    public void setConnectionPoint(ConnectionPoint connectionPoint) {
        this.connectionPoint = connectionPoint;
    }

}
