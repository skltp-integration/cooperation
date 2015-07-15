package se.skltp.cooperation.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A Cooperation.
 *
 * @author Peter Merikan
 */
@Entity
@Table(name = "COOPERATION")
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
