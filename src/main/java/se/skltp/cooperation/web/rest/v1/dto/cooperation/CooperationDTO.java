package se.skltp.cooperation.web.rest.v1.dto.cooperation;

import se.skltp.cooperation.web.rest.v1.dto.ConnectionPointDTO;
import se.skltp.cooperation.web.rest.v1.dto.LogicalAddressDTO;
import se.skltp.cooperation.web.rest.v1.dto.ServiceConsumerDTO;
import se.skltp.cooperation.web.rest.v1.dto.ServiceContractDTO;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A Cooperation Data Transfer Object
 *
 * @author Peter Merikan
 */
@XmlRootElement(name = "cooperation")
public class CooperationDTO {
    private Long id;
    private se.skltp.cooperation.web.rest.v1.dto.ServiceConsumerDTO serviceConsumer;
    private LogicalAddressDTO logicalAddress;
    private ConnectionPointDTO connectionPoint;
    private ServiceContractDTO serviceContract;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public se.skltp.cooperation.web.rest.v1.dto.ServiceConsumerDTO getServiceConsumer() {
        return serviceConsumer;
    }

    public void setServiceConsumer(ServiceConsumerDTO serviceConsumer) {
        this.serviceConsumer = serviceConsumer;
    }

    public LogicalAddressDTO getLogicalAddress() {
        return logicalAddress;
    }

    public void setLogicalAddress(LogicalAddressDTO logicalAddress) {
        this.logicalAddress = logicalAddress;
    }

    public ConnectionPointDTO getConnectionPoint() {
        return connectionPoint;
    }

    public void setConnectionPoint(ConnectionPointDTO connectionPoint) {
        this.connectionPoint = connectionPoint;
    }

    public ServiceContractDTO getServiceContract() {
        return serviceContract;
    }

    public void setServiceContract(ServiceContractDTO serviceContract) {
        this.serviceContract = serviceContract;
    }
}
