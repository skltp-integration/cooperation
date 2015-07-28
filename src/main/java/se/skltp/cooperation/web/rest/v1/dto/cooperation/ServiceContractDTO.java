package se.skltp.cooperation.web.rest.v1.dto.cooperation;

import se.skltp.cooperation.web.rest.v1.dto.CooperationDTO;
import se.skltp.cooperation.web.rest.v1.dto.ServiceProductionDTO;

import java.util.HashSet;
import java.util.Set;

/**
 * A ServiceContract Data Transfer Object
 *
 * @author Peter Merikan
 */
public class ServiceContractDTO {

    private Long id;
    private String name;
    private String namespace;
    private Integer major;
    private Integer minor;
//    private Set<CooperationDTO> cooperations = new HashSet<>();
//    private Set<ServiceProductionDTO> serviceProductions = new HashSet<>();

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

//    public Set<CooperationDTO> getCooperations() {
//        return cooperations;
//    }
//
//    public void setCooperations(Set<CooperationDTO> cooperations) {
//        this.cooperations = cooperations;
//    }
//
//    public Set<ServiceProductionDTO> getServiceProductions() {
//        return serviceProductions;
//    }
//
//    public void setServiceProductions(Set<ServiceProductionDTO> serviceProductions) {
//        this.serviceProductions = serviceProductions;
//    }
}
