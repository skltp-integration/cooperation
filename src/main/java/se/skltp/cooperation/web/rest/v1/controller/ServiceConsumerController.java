package se.skltp.cooperation.web.rest.v1.controller;


import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.skltp.cooperation.domain.ServiceConsumer;
import se.skltp.cooperation.service.ServiceConsumerCriteria;
import se.skltp.cooperation.service.ServiceConsumerService;
import se.skltp.cooperation.web.rest.exception.ResourceNotFoundException;
import se.skltp.cooperation.web.rest.v1.dto.ServiceConsumerDTO;
import se.skltp.cooperation.web.rest.v1.dto.ServiceConsumerListDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing ServiceConsumer.
 *
 * @author Peter Merikan
 */
@RestController
@RequestMapping("/v1/serviceConsumers")
public class ServiceConsumerController {

	private final Logger log = LoggerFactory.getLogger(ServiceConsumerController.class);

	private final ServiceConsumerService serviceConsumerService;
	private final DozerBeanMapper mapper;

	@Autowired
	public ServiceConsumerController(ServiceConsumerService serviceConsumerService, DozerBeanMapper mapper) {
		this.serviceConsumerService = serviceConsumerService;
		this.mapper = mapper;
	}

	/**
	 * GET  /connectionPoints -> get all the connectionPoints.
	 * Content type: JSON
	 */
	@RequestMapping(method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ServiceConsumerDTO> getAllAsJson(
		@RequestParam(value = "connectionPointId", required = false) Long connectionPointId) {
		log.debug("REST request to get all ServiceConsumers as Json");
		List<ServiceConsumerDTO> result = new ArrayList<>();

		ServiceConsumerCriteria criteria = new ServiceConsumerCriteria();
		criteria.setConnectionPointId(connectionPointId);
		List<ServiceConsumer> consumers = new ArrayList<>();
		if (connectionPointId != null) {
			consumers = serviceConsumerService.findAll(criteria);
		} else {
			consumers = serviceConsumerService.findAll();
		}
		for (ServiceConsumer consumer : consumers) {
			result.add(mapper.map(consumer, ServiceConsumerDTO.class));
		}
		return result;

	}

	/**
	 * GET  /connectionPoints -> get all the connectionPoints.
	 * Content type: XML
	 * <p/>
	 * TODO: add request param connectionPointId
	 */
	@RequestMapping(method = RequestMethod.GET,
		produces = MediaType.APPLICATION_XML_VALUE)
	public ServiceConsumerListDTO getAllAsXml() {
		log.debug("REST request to get all ServiceConsumers as Xml");
		ServiceConsumerListDTO result = new ServiceConsumerListDTO();
		List<ServiceConsumer> consumers = serviceConsumerService.findAll();
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
	public ServiceConsumerDTO get(@PathVariable Long id) {
		log.debug("REST request to get ConnectionPoint : {}", id);
		ServiceConsumer serviceConsumer = serviceConsumerService.find(id);
		if (serviceConsumer == null) {
			log.debug("Service consumer with id {} not found", id);
			throw new ResourceNotFoundException("Service consumer with id " + id + " not found");

		}
		return mapper.map(serviceConsumer, ServiceConsumerDTO.class);
	}

}
