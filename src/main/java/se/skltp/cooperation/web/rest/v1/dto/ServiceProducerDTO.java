package se.skltp.cooperation.web.rest.v1.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * A ServiceProducer Data Transfer Object
 *
 * @author Peter Merikan
 */
public class ServiceProducerDTO {

    private Long id;
    private String description;
    private Set<ServiceProductionDTO> serviceProductions = new HashSet<>();

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

    public Set<ServiceProductionDTO> getServiceProductions() {
        return serviceProductions;
    }

    public void setServiceProductions(Set<ServiceProductionDTO> serviceProductions) {
        this.serviceProductions = serviceProductions;
    }
}
