package se.skltp.cooperation.web.rest.v1.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.Cooperation;

import java.util.HashSet;
import java.util.Set;

/**
 * A ServiceConsumer Data Transfer Object
 *
 * @author Peter Merikan
 */
public class ServiceConsumerDTO {

    private Long id;
    private String description;
    private String hsaId;
    @JsonManagedReference
    private Set<CooperationDTO> cooperations = new HashSet<>();
    @JsonBackReference
    private ConnectionPointDTO connectionPoint;

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

    public Set<CooperationDTO> getCooperations() {
        return cooperations;
    }

    public void setCooperations(Set<CooperationDTO> cooperations) {
        this.cooperations = cooperations;
    }

    public ConnectionPointDTO getConnectionPoint() {
        return connectionPoint;
    }

    public void setConnectionPoint(ConnectionPointDTO connectionPoint) {
        this.connectionPoint = connectionPoint;
    }
}
