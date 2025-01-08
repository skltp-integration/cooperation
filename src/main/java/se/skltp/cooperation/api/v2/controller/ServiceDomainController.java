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

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import se.skltp.cooperation.api.v2.listdto.ServiceDomainListDTO;
import se.skltp.cooperation.domain.ServiceDomain;
import se.skltp.cooperation.service.ServiceDomainCriteria;
import se.skltp.cooperation.service.ServiceDomainService;
import se.skltp.cooperation.api.exception.ResourceNotFoundException;
import se.skltp.cooperation.api.v2.dto.ServiceDomainDTO;

/**
 * REST controller for managing ServiceDomains.
 *
 * @author Jan Vasternas
 */
@RestController
@RequestMapping(value = {
	"/api/v2/serviceDomains",
	"/api/v2/serviceDomains/",
	"/api/v2/serviceDomains.json",
	"/api/v2/serviceDomains.xml"
})
public class ServiceDomainController {

	private final Logger log = LoggerFactory.getLogger(ServiceDomainController.class);

	private final ServiceDomainService serviceDomainService;
	private final ModelMapper mapper;

	@Autowired
	public ServiceDomainController(ServiceDomainService serviceDomainService,
			ModelMapper mapper) {
		this.serviceDomainService = serviceDomainService;
		this.mapper = mapper;
	}

	/**
	 * GET /serviceDomains -> get all the serviceDomains as json
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ServiceDomainDTO> getAllAsJson(@RequestParam(required = false) String namespace) {
		log.debug("REST request to get all ServiceDomains as json");

		return getAll(namespace);

	}

	/**
	 * GET /serviceDomains -> get all the serviceDomains as xml
	 */
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public ServiceDomainListDTO getAllAsXml(@RequestParam(required = false) String namespace) {
		log.debug("REST request to get all ServiceDomains as xml");

		return new ServiceDomainListDTO(getAll(namespace));

	}

	/**
	 * GET /serviceDomains/:id -> get the "id" serviceDomain. Content type
	 * from Accept header
	 */
	@GetMapping(value = { "/{id}", "/{id}.json", "/{id}.xml" }, produces = {
		MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ServiceDomainDTO getOne(@PathVariable Long id) {
		log.debug("REST request to get ServiceDomain : {}", id);

		ServiceDomain cp = serviceDomainService.find(id);
		if (cp == null) {
			log.debug("Connection point with id {} not found", id);
			throw new ResourceNotFoundException("Connection point with id " + id + " not found");
		}
		return toDTO(cp);
	}

	private List<ServiceDomainDTO> getAll(String namespace) {

		ServiceDomainCriteria criteria = new ServiceDomainCriteria(namespace);
		List<ServiceDomain> serviceDomains = serviceDomainService.findAll(criteria);

		List<ServiceDomainDTO> result = new ArrayList<>();
		for (ServiceDomain cp : serviceDomains) {
			result.add(toDTO(cp));
		}
		return result;
	}

	private ServiceDomainDTO toDTO(ServiceDomain cp) {
		return mapper.map(cp, ServiceDomainDTO.class);
	}

}
