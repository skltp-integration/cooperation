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
import se.skltp.cooperation.api.v2.dto.InstalledContractDTO;
import se.skltp.cooperation.api.v2.listdto.InstalledContractListDTO;
import se.skltp.cooperation.domain.InstalledContract;
import se.skltp.cooperation.service.InstalledContractCriteria;
import se.skltp.cooperation.service.InstalledContractService;

/**
 * REST controller for managing ServiceContract.
 *
 * @author Jan Vasternas
 */
@RestController
@RequestMapping(value = {
	"/api/v2/installedContracts",
	"/api/v2/installedContracts/",
	"/api/v2/installedContracts.json",
	"/api/v2/installedContracts.xml"
})
public class InstalledContractController {

	private final Logger log = LoggerFactory.getLogger(InstalledContractController.class);

	private final InstalledContractService installedContractService;
	private final DozerBeanMapper mapper;

	@Autowired
	public InstalledContractController(InstalledContractService installedContractService,
			DozerBeanMapper mapper) {
		this.installedContractService = installedContractService;
		this.mapper = mapper;
	}

	/**
	 * GET /installedContracts -> get all the installedContracts. Content type:
	 * JSON
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<InstalledContractDTO> getAllAsJson(
			@RequestParam(required = false) Long connectionPointId,
			@RequestParam(required = false) Long serviceContractId,
			@RequestParam(required = false) Long serviceDomainId) {
		log.debug("REST request to get all InstalledContracts as json");

		return getAll(connectionPointId, serviceContractId, serviceDomainId);

	}

	/**
	 * GET /serviceContracts -> get all the serviceContracts. Content type: XML
	 */
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public InstalledContractListDTO getAllAsXml(
			@RequestParam(required = false) Long connectionPointId,
			@RequestParam(required = false) Long serviceContractId,
			@RequestParam(required = false) Long serviceDomainId) {
		log.debug("REST request to get all ServiceContracts as xml");

		InstalledContractListDTO result = new InstalledContractListDTO();
		result.setInstalledContracts(getAll(connectionPointId, serviceContractId, serviceDomainId));
		return result;

	}

	/**
	 * GET /serviceContracts/:id -> get the "id" serviceContract.
	 */
	@GetMapping(value = { "/{id}", "/{id}.json", "/{id}.xml" }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public InstalledContractDTO get(@PathVariable Long id) {
		log.debug("REST request to get ServiceContract : {}", id);

		InstalledContract cp = installedContractService.find(id);
		if (cp == null) {
			log.debug("Installed Contract with id {} not found", id);
			throw new ResourceNotFoundException("Installed Contract with id " + id + " not found");
		}
		return toDTO(cp);
	}

	private List<InstalledContractDTO> getAll( Long connectionPointId, Long serviceContractId,
			Long serviceDomainId) {

		InstalledContractCriteria criteria = new InstalledContractCriteria(connectionPointId, serviceContractId,
				serviceDomainId);
		List<InstalledContract> installedContracts = installedContractService.findAll(criteria);
		List<InstalledContractDTO> result = new ArrayList<>();
		for (InstalledContract cp : installedContracts) {
			result.add(toDTO(cp));
		}
		return result;
	}

	private InstalledContractDTO toDTO(InstalledContract cp) {
		return mapper.map(cp, InstalledContractDTO.class);
	}

}
