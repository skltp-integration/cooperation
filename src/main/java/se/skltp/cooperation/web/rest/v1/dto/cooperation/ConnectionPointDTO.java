package se.skltp.cooperation.web.rest.v1.dto.cooperation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import se.skltp.cooperation.web.rest.v1.dto.*;
import se.skltp.cooperation.web.rest.v1.dto.ServiceConsumerDTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * A ConnectionPoint Data Transfer Object
 *
 * @author Peter Merikan
 */
@XmlRootElement(name = "connectionPoint")
public class ConnectionPointDTO {


    private Long id;
    private String platform;
    private String environment;
    @JsonManagedReference
    private Set<ServiceProductionDTO> serviceProductions = new HashSet<>();
    @JsonManagedReference
    private Set<se.skltp.cooperation.web.rest.v1.dto.ServiceConsumerDTO> serviceConsumers = new HashSet<>();
//    @JsonManagedReference
    @JsonIgnore
    private Set<CooperationDTO> cooperations = new HashSet<>();

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

    public Set<ServiceProductionDTO> getServiceProductions() {
        return serviceProductions;
    }

    public void setServiceProductions(Set<ServiceProductionDTO> serviceProductions) {
        this.serviceProductions = serviceProductions;
    }

    public Set<se.skltp.cooperation.web.rest.v1.dto.ServiceConsumerDTO> getServiceConsumers() {
        return serviceConsumers;
    }

    public void setServiceConsumers(Set<ServiceConsumerDTO> serviceConsumers) {
        this.serviceConsumers = serviceConsumers;
    }

    public Set<CooperationDTO> getCooperations() {
        return cooperations;
    }

    public void setCooperations(Set<CooperationDTO> cooperations) {
        this.cooperations = cooperations;
    }
}
