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
package se.skltp.cooperation.api.v2.controller;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import se.skltp.cooperation.api.exception.ResourceNotFoundException;
import se.skltp.cooperation.api.v2.dto.ServiceContractDTO;
import se.skltp.cooperation.api.v2.listdto.ServiceContractListDTO;
import se.skltp.cooperation.domain.ServiceContract;
import se.skltp.cooperation.service.ServiceContractCriteria;
import se.skltp.cooperation.service.ServiceContractService;

/**
 * REST controller for managing ServiceContract.
 *
 * @author Jan Vasternas
 */
@RestController
@RequestMapping(value = {
	"/api/v2/serviceContracts",
	"/api/v2/serviceContracts.json",
	"/api/v2/serviceContracts.xml"
})
public class ServiceContractController {

	private final Logger log = LoggerFactory.getLogger(ServiceContractController.class);

	private final ServiceContractService serviceContractService;
	private final DozerBeanMapper mapper;

	@Autowired
	public ServiceContractController(ServiceContractService serviceContractService,
			DozerBeanMapper mapper) {
		this.serviceContractService = serviceContractService;
		this.mapper = mapper;
	}

	/**
	 * GET /serviceContracts -> get all the serviceContracts. Content type: JSON
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ServiceContractDTO> getAllAsJson(@RequestParam(required = false) String namespace,
												 @RequestParam(required = false) Long connectionPointId,
												 @RequestParam(required = false) Long logicalAddressId,
												 @RequestParam(required = false) Long serviceConsumerId,
												 @RequestParam(required = false) Long serviceProducerId,
												 @RequestParam(required = false) Long serviceDomainId) {
		log.debug("REST request to get all ServiceContracts as json");

		return getAll(namespace, connectionPointId, logicalAddressId, serviceConsumerId,
				serviceProducerId, serviceDomainId);

	}

	/**
	 * GET /serviceContracts -> get all the serviceContracts. Content type: XML
	 */
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public ServiceContractListDTO getAllAsXml(@RequestParam(required = false) String namespace,
											  @RequestParam(required = false) Long connectionPointId,
											  @RequestParam(required = false) Long logicalAddressId,
											  @RequestParam(required = false) Long serviceConsumerId,
											  @RequestParam(required = false) Long serviceProducerId,
											  @RequestParam(required = false) Long serviceDomainId) {
		log.debug("REST request to get all ServiceContracts as xml");

		ServiceContractListDTO result = new ServiceContractListDTO();
		result.setServiceContracts(getAll(namespace, connectionPointId, logicalAddressId,
				serviceConsumerId, serviceProducerId, serviceDomainId));
		return result;

	}

	/**
	 * GET /serviceContracts/:id -> get the "id" serviceContract.
	 */
	@GetMapping(value = { "/{id}", "/{id}.json", "/{id}.xml" }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ServiceContractDTO get(@PathVariable Long id) {
		log.debug("REST request to get ServiceContract : {}", id);

		ServiceContract cp = serviceContractService.find(id);
		if (cp == null) {
			log.debug("Connection point with id {} not found", id);
			throw new ResourceNotFoundException("Connection point with id " + id + " not found");
		}
		return toDTO(cp);
	}

	private List<ServiceContractDTO> getAll(String namespace, Long connectionPointId,
			Long logicalAddressId, Long serviceConsumerId, Long serviceProducerId,
			Long serviceDomainId) {

		ServiceContractCriteria criteria = new ServiceContractCriteria(namespace,
				serviceConsumerId, logicalAddressId, connectionPointId, serviceProducerId,
				serviceDomainId);
		List<ServiceContract> serviceContracts = serviceContractService.findAll(criteria);
		List<ServiceContractDTO> result = new ArrayList<>();
		for (ServiceContract cp : serviceContracts) {
			result.add(toDTO(cp));
		}
		return result;
	}

	private ServiceContractDTO toDTO(ServiceContract cp) {
		return mapper.map(cp, ServiceContractDTO.class);
	}

}
