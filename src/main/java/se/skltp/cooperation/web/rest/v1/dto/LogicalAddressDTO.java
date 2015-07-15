package se.skltp.cooperation.web.rest.v1.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * A LogicalAddress Data Transfer Object
 *
 * @author Peter Merikan
 */
public class LogicalAddressDTO {

    private Long id;
    private String description;
    private Set<CooperationDTO> cooperations = new HashSet<>();
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

    public Set<CooperationDTO> getCooperations() {
        return cooperations;
    }

    public void setCooperations(Set<CooperationDTO> cooperations) {
        this.cooperations = cooperations;
    }

    public Set<ServiceProductionDTO> getServiceProductions() {
        return serviceProductions;
    }

    public void setServiceProductions(Set<ServiceProductionDTO> serviceProductions) {
        this.serviceProductions = serviceProductions;
    }
}
