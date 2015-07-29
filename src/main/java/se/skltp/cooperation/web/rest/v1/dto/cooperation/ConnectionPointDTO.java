package se.skltp.cooperation.web.rest.v1.dto.cooperation;

import javax.xml.bind.annotation.XmlRootElement;

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

}
