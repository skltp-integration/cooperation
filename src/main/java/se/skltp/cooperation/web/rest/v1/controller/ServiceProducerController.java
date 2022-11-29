/**
 * Copyright (c) 2014 Center for eHalsa i samverkan (CeHis).
 * 								<http://cehis.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
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

import se.skltp.cooperation.domain.ServiceProducer;
import se.skltp.cooperation.service.ServiceProducerCriteria;
import se.skltp.cooperation.service.ServiceProducerService;
import se.skltp.cooperation.web.rest.exception.ResourceNotFoundException;
import se.skltp.cooperation.web.rest.v1.dto.ServiceProducerDTO;
import se.skltp.cooperation.web.rest.v1.listdto.ServiceProducerListDTO;

/**
 * REST controller for managing ServiceProducer.
 *
 * @author Jan Vasternas
 */
@RestController
@RequestMapping(value = {
	"/api/v1/serviceProducers",
	"/api/v1/serviceProducers.json",
	"/api/v1/serviceProducers.xml",
	"/api/v2/serviceProducers",
	"/api/v2/serviceProducers.json",
	"/api/v2/serviceProducers.xml"
})
public class ServiceProducerController {

	private final Logger log = LoggerFactory.getLogger(ServiceProducerController.class);

	private final ServiceProducerService serviceProducerService;
	private final DozerBeanMapper mapper;

	@Autowired
	public ServiceProducerController(ServiceProducerService serviceProducerService,
			DozerBeanMapper mapper) {
		this.serviceProducerService = serviceProducerService;
		this.mapper = mapper;
	}

	/**
	 * GET /connectionPoints -> get all the connectionPoints. Content type: JSON
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ServiceProducerDTO> getAllAsJson(@RequestParam(required = false) String hsaId,
			@RequestParam(required = false) Long connectionPointId,
			@RequestParam(required = false) Long logicalAddressId,
			@RequestParam(required = false) Long serviceContractId,
			@RequestParam(required = false) Long serviceConsumerId) {
		log.debug("REST request to get all ServiceProducers as json");

		return getAll(hsaId, connectionPointId, logicalAddressId, serviceContractId,
				serviceConsumerId);

	}

	/**
	 * GET /connectionPoints -> get all the connectionPoints. Content type: XML
	 * <p/>
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public ServiceProducerListDTO getAllAsXml(@RequestParam(required = false) String hsaId,
			@RequestParam(required = false) Long connectionPointId,
			@RequestParam(required = false) Long logicalAddressId,
			@RequestParam(required = false) Long serviceContractId,
			@RequestParam(required = false) Long serviceConsumerId) {
		log.debug("REST request to get all ServiceProducers as xml");

		ServiceProducerListDTO result = new ServiceProducerListDTO();
		result.setServiceProducers(getAll(hsaId, connectionPointId, logicalAddressId,
				serviceContractId, serviceConsumerId));
		return result;

	}

	/**
	 * GET /serviceProducers/:id -> get the "id" serviceProducer.
	 */
	@RequestMapping(value = { "/{id}", "/{id}.json", "/{id}.xml" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ServiceProducerDTO get(@PathVariable Long id) {
		log.debug("REST request to get ConnectionPoint : {}", id);

		ServiceProducer serviceProducer = serviceProducerService.find(id);
		if (serviceProducer == null) {
			log.debug("Service Producer with id {} not found", id);
			throw new ResourceNotFoundException("Service Producer with id " + id + " not found");

		}
		return toDTO(serviceProducer);
	}

	private List<ServiceProducerDTO> getAll(String hsaId, Long connectionPointId,
			Long logicalAddressId, Long serviceContractId, Long serviceConsumerId) {

		ServiceProducerCriteria criteria = new ServiceProducerCriteria(hsaId, connectionPointId,
				logicalAddressId, serviceContractId, serviceConsumerId);
		List<ServiceProducer> producers = serviceProducerService.findAll(criteria);
		List<ServiceProducerDTO> result = new ArrayList<>();
		for (ServiceProducer producer : producers) {
			if ( ! (connectionPointId == null)) {
				producer.setConnectionPoint(null);
			}
			result.add(toDTO(producer));
		}
		return result;
	}

	private ServiceProducerDTO toDTO(ServiceProducer Producer) {
		return mapper.map(Producer, ServiceProducerDTO.class);
	}

}
