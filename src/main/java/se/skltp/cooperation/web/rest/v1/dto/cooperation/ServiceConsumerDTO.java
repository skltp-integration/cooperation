package se.skltp.cooperation.web.rest.v1.dto.cooperation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import se.skltp.cooperation.web.rest.v1.dto.ConnectionPointDTO;
import se.skltp.cooperation.web.rest.v1.dto.CooperationDTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * A ServiceConsumer Data Transfer Object
 *
 * @author Peter Merikan
 */
@XmlRootElement(name = "serviceConsumer")
public class ServiceConsumerDTO {

    private Long id;
    private String description;
    private String hsaId;

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

}
