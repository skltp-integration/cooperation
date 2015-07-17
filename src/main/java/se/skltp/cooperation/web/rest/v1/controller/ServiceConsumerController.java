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
import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.ServiceConsumer;
import se.skltp.cooperation.repository.ConnectionPointRepository;
import se.skltp.cooperation.repository.ServiceConsumerRepository;
import se.skltp.cooperation.web.rest.v1.dto.ConnectionPointDTO;
import se.skltp.cooperation.web.rest.v1.dto.ConnectionPointListDTO;
import se.skltp.cooperation.web.rest.v1.dto.ServiceConsumerDTO;
import se.skltp.cooperation.web.rest.v1.dto.ServiceConsumerListDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ServiceConsumer.
 *
 * @author Peter Merikan
 */
@RestController
@RequestMapping("/v1/serviceConsumers")
public class ServiceConsumerController {

    private final Logger log = LoggerFactory.getLogger(ServiceConsumerController.class);

    @Autowired
    private ServiceConsumerRepository serviceConsumerRepository;

    @Autowired
    private DozerBeanMapper mapper;



    /**
     * GET  /connectionPoints -> get all the connectionPoints.
     * Content type: JSON
     */
    @RequestMapping(method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ServiceConsumerDTO> getAllAsJson() {
        log.debug("REST request to get all ServiceConsumers as Json");
        List<ServiceConsumerDTO> result = new ArrayList<>();
        List<ServiceConsumer> consumers = serviceConsumerRepository.findAll();
        for (ServiceConsumer consumer : consumers) {
            result.add(mapper.map(consumer, ServiceConsumerDTO.class));
        }
        return result;

    }
    /**
     * GET  /connectionPoints -> get all the connectionPoints.
     * Content type: XML
     */
    @RequestMapping(method = RequestMethod.GET,
        produces = MediaType.APPLICATION_XML_VALUE)
    public ServiceConsumerListDTO getAllAsXml() {
        log.debug("REST request to get all ServiceConsumers as Xml");
        ServiceConsumerListDTO result = new ServiceConsumerListDTO();
        List<ServiceConsumer> consumers = serviceConsumerRepository.findAll();
        for (ServiceConsumer consumer : consumers) {
            result.getServiceConsumers().add(mapper.map(consumer, ServiceConsumerDTO.class));
        }
        return result;

    }

    /**
     * GET  /serviceConsumers/:id -> get the "id" serviceConsumer.
     */
    @RequestMapping(value = "/{id}",
        method = RequestMethod.GET,
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ServiceConsumerDTO> get(@PathVariable Long id) {
        log.debug("REST request to get ConnectionPoint : {}", id);
        return Optional.ofNullable(serviceConsumerRepository.findOne(id))
            .map(serviceConsumer -> new ResponseEntity<>(
                mapper.map(serviceConsumer, ServiceConsumerDTO.class),
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
