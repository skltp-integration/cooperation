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

import se.skltp.cooperation.domain.LogicalAddress;
import se.skltp.cooperation.service.LogicalAddressCriteria;
import se.skltp.cooperation.service.LogicalAddressService;
import se.skltp.cooperation.api.exception.ResourceNotFoundException;
import se.skltp.cooperation.api.v2.dto.LogicalAddressDTO;
import se.skltp.cooperation.api.v2.listdto.LogicalAddressListDTO;

/**
 * REST controller for managing LogicalAddress.
 *
 * @author Jan Vasternas
 */
@RestController
@RequestMapping(value = {
	"/api/v2/logicalAddresses", // Fixed addresses for v2.
	"/api/v2/logicalAddresses/",
	"/api/v2/logicalAddresses.json",
	"/api/v2/logicalAddresses.xml"
})
public class LogicalAddressController {

	private final Logger log = LoggerFactory.getLogger(LogicalAddressController.class);

	private final LogicalAddressService logicalAddressService;
	private final ModelMapper mapper;

	@Autowired
	public LogicalAddressController(LogicalAddressService logicalAddressService,
			ModelMapper mapper) {
		this.logicalAddressService = logicalAddressService;
		this.mapper = mapper;
	}

	/**
	 * GET /logicalAddresss -> get all the logicalAddresss. Content type: JSON
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<LogicalAddressDTO> getAllAsJson(
			@RequestParam(required = false) String logicalAdress,
			@RequestParam(required = false) Long connectionPointId,
			@RequestParam(required = false) Long serviceContractId,
			@RequestParam(required = false) Long serviceConsumerId,
			@RequestParam(required = false) Long serviceProducerId) {
		log.debug("REST request to get all LogicalAddresses as json");

		return getAll(logicalAdress, connectionPointId, serviceContractId, serviceConsumerId,
				serviceProducerId);

	}


	/**
	 * GET /logicalAddresss -> get all the logicalAddresss. Content type: XML
	 */
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public LogicalAddressListDTO getAllAsXml(
			@RequestParam(required = false) String logicalAdress,
			@RequestParam(required = false) Long connectionPointId,
			@RequestParam(required = false) Long serviceContractId,
			@RequestParam(required = false) Long serviceConsumerId,
			@RequestParam(required = false) Long serviceProducerId) {
		log.debug("REST request to get all LogicalAddresses as xml");

		LogicalAddressListDTO result = new LogicalAddressListDTO();
		result.setLogicalAddresss(getAll(logicalAdress, connectionPointId, serviceContractId, serviceConsumerId,
				serviceProducerId));
		return result;

	}

	/**
	 * GET /logicalAddresss/:id -> get the "id" logicalAddress.
	 */
	@GetMapping(value = { "/{id}", "/{id}.json", "/{id}.xml" }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public LogicalAddressDTO get(@PathVariable Long id) {
		log.debug("REST request to get LogicalAddress : {}", id);

		LogicalAddress cp = logicalAddressService.find(id);
		if (cp == null) {
			log.debug("Logical Adress with id {} not found", id);
			throw new ResourceNotFoundException("Logical Adress with id " + id + " not found");
		}
		return toDTO(cp);
	}

	private List<LogicalAddressDTO> getAll(String logicalAdress, Long connectionPointId,
			Long serviceContractId, Long serviceConsumerId, Long serviceProducerId) {
		LogicalAddressCriteria criteria = new  LogicalAddressCriteria(logicalAdress, serviceConsumerId,
				serviceContractId,  connectionPointId,  serviceProducerId);
		List<LogicalAddress> logicalAddresss = logicalAddressService.findAll(criteria);
		List<LogicalAddressDTO> result = new ArrayList<>();
		for (LogicalAddress la : logicalAddresss) {
			result.add(toDTO(la));
		}
		return result;
	}

	private LogicalAddressDTO toDTO(LogicalAddress la) {
		return mapper.map(la, LogicalAddressDTO.class);
	}

}
