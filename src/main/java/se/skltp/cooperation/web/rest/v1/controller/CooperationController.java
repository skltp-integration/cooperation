package se.skltp.cooperation.web.rest.v1.controller;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.skltp.cooperation.domain.Cooperation;
import se.skltp.cooperation.repository.CooperationRepository;
import se.skltp.cooperation.web.rest.v1.dto.ConnectionPointDTO;
import se.skltp.cooperation.web.rest.v1.dto.CooperationDTO;
import se.skltp.cooperation.web.rest.v1.dto.CooperationListDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller to handle resource Cooperation
 */
@RestController
@RequestMapping("/v1/cooperations")
public class CooperationController {

    private final Logger log = LoggerFactory.getLogger(CooperationController.class);

    @Autowired
    private CooperationRepository cooperationRepository;

    @Autowired
    private DozerBeanMapper mapper;

    /**
     * GET  /cooperations -> get all the cooperations.
     * Content type: JSON
     */
    @RequestMapping(method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CooperationDTO> getAllAsJson() {
        log.debug("REST request to get all Cooperations");
        List<CooperationDTO> result = new ArrayList<>();
        List<Cooperation> cooperations = cooperationRepository.findAll();
        for (Cooperation cp : cooperations) {
            result.add(mapper.map(cp, CooperationDTO.class));
        }
        return result;

    }

    /**
     * GET  /cooperations -> get all cooperations.
     * Content type: XML
     */
    @RequestMapping(method = RequestMethod.GET,
        produces = MediaType.APPLICATION_XML_VALUE)
    public CooperationListDTO getAllAsXml() {
        log.debug("REST request to get all Cooperations as {}", MediaType.APPLICATION_XML);
        CooperationListDTO result = new CooperationListDTO();

        List<Cooperation> cooperations = cooperationRepository.findAll();
        for (Cooperation c : cooperations) {
            result.getCooperations().add(mapper.map(c, CooperationDTO.class));
        }

        return result;

    }

    /**
     * GET  /cooperations/:id -> get the "id" cooperation.
     */
    @RequestMapping(value = "/{id}",
        method = RequestMethod.GET,
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CooperationDTO> get(@PathVariable Long id) {
        log.debug("REST request to get ConnectionPoint : {}", id);
        return Optional.ofNullable(cooperationRepository.findOne(id))
            .map(cooperation -> new ResponseEntity<>(
                mapper.map(cooperation, CooperationDTO.class),
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
