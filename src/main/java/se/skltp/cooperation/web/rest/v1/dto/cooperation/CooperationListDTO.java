package se.skltp.cooperation.web.rest.v1.dto.cooperation;


import se.skltp.cooperation.web.rest.v1.dto.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper object to hold a list of {@link se.skltp.cooperation.web.rest.v1.dto.CooperationDTO} objects.
 */
@XmlRootElement(name = "cooperations")
public class CooperationListDTO {

    private List<se.skltp.cooperation.web.rest.v1.dto.CooperationDTO> cooperations = new ArrayList<>();

    @XmlElement(name = "cooperation")
    public List<se.skltp.cooperation.web.rest.v1.dto.CooperationDTO> getCooperations() {
        return cooperations;
    }

    public void setCooperations(List<se.skltp.cooperation.web.rest.v1.dto.CooperationDTO> cooperations) {
        this.cooperations = cooperations;
    }


}
