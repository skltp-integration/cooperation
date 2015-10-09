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

import se.skltp.cooperation.domain.ServiceContract;
import se.skltp.cooperation.service.ServiceContractCriteria;
import se.skltp.cooperation.service.ServiceContractService;
import se.skltp.cooperation.web.rest.exception.ResourceNotFoundException;
import se.skltp.cooperation.web.rest.v1.dto.ServiceContractDTO;
import se.skltp.cooperation.web.rest.v1.listdto.ServiceContractListDTO;

/**
 * REST controller for managing ServiceContract.
 *
 * @author Jan Vasternas
 */
@RestController
@RequestMapping(value = { "/api/v1/serviceContracts", "/api/v1/serviceContracts.json",
		"/api/v1/serviceContracts.xml" })
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
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ServiceContractDTO> getAllAsJson(
			@RequestParam(required = false) String namespace,
			@RequestParam(required = false) Long connectionPointId,
			@RequestParam(required = false) Long logicalAddressId,
			@RequestParam(required = false) Long serviceConsumerId,
			@RequestParam(required = false) Long serviceProducerId) {
		log.debug("REST request to get all ServiceContracts as json");

		return getAll(namespace, connectionPointId, logicalAddressId, serviceConsumerId,
				serviceProducerId);

	}


	/**
	 * GET /serviceContracts -> get all the serviceContracts. Content type: XML
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public ServiceContractListDTO getAllAsXml(
			@RequestParam(required = false) String namespace,
			@RequestParam(required = false) Long connectionPointId,
			@RequestParam(required = false) Long logicalAddressId,
			@RequestParam(required = false) Long serviceConsumerId,
			@RequestParam(required = false) Long serviceProducerId) {
		log.debug("REST request to get all ServiceContracts as xml");

		ServiceContractListDTO result = new ServiceContractListDTO();
		result.setServiceContracts(getAll(namespace, connectionPointId, logicalAddressId, serviceConsumerId,
				serviceProducerId));
		return result;

	}

	/**
	 * GET /serviceContracts/:id -> get the "id" serviceContract.
	 */
	@RequestMapping(value = { "/{id}", "/{id}.json", "/{id}.xml" }, method = RequestMethod.GET, produces = {
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
			Long logicalAddressId, Long serviceConsumerId, Long serviceProducerId) {
		ServiceContractCriteria criteria = new  ServiceContractCriteria(namespace, serviceConsumerId,
				 logicalAddressId,  connectionPointId,  serviceProducerId);
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
