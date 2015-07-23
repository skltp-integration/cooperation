package se.skltp.cooperation.web.rest.v1.dto;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper object to hold a list of {@link CooperationDTO} objects.
 */
@XmlRootElement(name = "cooperations")
public class CooperationListDTO {

    private List<CooperationDTO> cooperations = new ArrayList<>();

    @XmlElement(name = "cooperation")
    public List<CooperationDTO> getCooperations() {
        return cooperations;
    }

    public void setCooperations(List<CooperationDTO> cooperations) {
        this.cooperations = cooperations;
    }


}
