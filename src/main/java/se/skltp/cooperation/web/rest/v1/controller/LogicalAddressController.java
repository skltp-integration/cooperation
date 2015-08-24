package se.skltp.cooperation.web.rest.v1.controller;

import java.util.ArrayList;
import java.util.List;

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

import se.skltp.cooperation.domain.LogicalAddress;
import se.skltp.cooperation.service.LogicalAddressCriteria;
import se.skltp.cooperation.service.LogicalAddressService;
import se.skltp.cooperation.web.rest.exception.ResourceNotFoundException;
import se.skltp.cooperation.web.rest.v1.dto.LogicalAddressDTO;
import se.skltp.cooperation.web.rest.v1.listdto.LogicalAddressListDTO;

/**
 * REST controller for managing LogicalAddress.
 *
 * @author Jan Vasternas
 */
@RestController
@RequestMapping(value = { "/api/v1/logicalAddresss", "/api/v1/logicalAddresss.json",
		"/api/v1/logicalAddresss.xml" })
public class LogicalAddressController {

	private final Logger log = LoggerFactory.getLogger(LogicalAddressController.class);

	private final LogicalAddressService logicalAddressService;
	private final DozerBeanMapper mapper;

	@Autowired
	public LogicalAddressController(LogicalAddressService logicalAddressService,
			DozerBeanMapper mapper) {
		this.logicalAddressService = logicalAddressService;
		this.mapper = mapper;
	}

	/**
	 * GET /logicalAddresss -> get all the logicalAddresss. Content type: JSON
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<LogicalAddressDTO> getAllAsJson(
			@RequestParam(required = false) String namespace,
			@RequestParam(required = false) Long connectionPointId,
			@RequestParam(required = false) Long serviceContractId,
			@RequestParam(required = false) Long serviceConsumerId,
			@RequestParam(required = false) Long serviceProducerId) {
		log.debug("REST request to get all LogicalAddresss as json");

		return getAll(namespace, connectionPointId, serviceContractId, serviceConsumerId,
				serviceProducerId);

	}


	/**
	 * GET /logicalAddresss -> get all the logicalAddresss. Content type: XML
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public LogicalAddressListDTO getAllAsXml(
			@RequestParam(required = false) String namespace,
			@RequestParam(required = false) Long connectionPointId,
			@RequestParam(required = false) Long serviceContractId,
			@RequestParam(required = false) Long serviceConsumerId,
			@RequestParam(required = false) Long serviceProducerId) {
		log.debug("REST request to get all LogicalAddresss as xml");

		LogicalAddressListDTO result = new LogicalAddressListDTO();
		result.setLogicalAddresss(getAll(namespace, connectionPointId, serviceContractId, serviceConsumerId,
				serviceProducerId));
		return result;

	}

	/**
	 * GET /logicalAddresss/:id -> get the "id" logicalAddress.
	 */
	@RequestMapping(value = { "/{id}", "/{id}.json", "/{id}.xml" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public LogicalAddressDTO get(@PathVariable Long id) {
		log.debug("REST request to get LogicalAddress : {}", id);

		LogicalAddress cp = logicalAddressService.find(id);
		if (cp == null) {
			log.debug("Connection point with id {} not found", id);
			throw new ResourceNotFoundException("Connection point with id " + id + " not found");
		}
		return toDTO(cp);
	}

	private List<LogicalAddressDTO> getAll(String namespace, Long connectionPointId,
			Long serviceContractId, Long serviceConsumerId, Long serviceProducerId) {
		LogicalAddressCriteria criteria = new  LogicalAddressCriteria(namespace, serviceConsumerId,
				serviceContractId,  connectionPointId,  serviceProducerId);
		List<LogicalAddress> logicalAddresss = logicalAddressService.findAll(criteria);
		List<LogicalAddressDTO> result = new ArrayList<>(); 
		for (LogicalAddress cp : logicalAddresss) {
			result.add(toDTO(cp));
		}
		return result;
	}

	private LogicalAddressDTO toDTO(LogicalAddress cp) {
		return mapper.map(cp, LogicalAddressDTO.class);
	}

}
