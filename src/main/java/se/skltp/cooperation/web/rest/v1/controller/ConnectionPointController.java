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

import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.service.ConnectionPointCriteria;
import se.skltp.cooperation.service.ConnectionPointService;
import se.skltp.cooperation.web.rest.exception.ResourceNotFoundException;
import se.skltp.cooperation.web.rest.v1.dto.ConnectionPointDTO;
import se.skltp.cooperation.web.rest.v1.listdto.ConnectionPointListDTO;

/**
 * REST controller for managing ConnectionPoint.
 *
 * @author Peter Merikan
 */
@RestController
@RequestMapping(value = { "/api/v1/connectionPoints", "/api/v1/connectionPoints.json",
		"/api/v1/connectionPoints.xml" })
public class ConnectionPointController {

	private final Logger log = LoggerFactory.getLogger(ConnectionPointController.class);

	private final ConnectionPointService connectionPointService;
	private final DozerBeanMapper mapper;

	@Autowired
	public ConnectionPointController(ConnectionPointService connectionPointService,
			DozerBeanMapper mapper) {
		this.connectionPointService = connectionPointService;
		this.mapper = mapper;
	}

	/**
	 * GET /connectionPoints -> get all the connectionPoints as json
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ConnectionPointDTO> getAllAsJson(@RequestParam(required = false) String platform,
			@RequestParam(required = false) String environment,
			@RequestParam(required = false) Long serviceConsumerId,
			@RequestParam(required = false) Long logicalAddressId,
			@RequestParam(required = false) Long serviceContractId,
			@RequestParam(required = false) Long serviceProducerId) {
		log.debug("REST request to get all ConnectionPoints as json");

		return getAll(platform, environment, serviceConsumerId, logicalAddressId,
				serviceContractId, serviceProducerId);

	}

	/**
	 * GET /connectionPoints -> get all the connectionPoints as xml
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public ConnectionPointListDTO getAllAsXml(@RequestParam(required = false) String platform,
			@RequestParam(required = false) String environment,
			@RequestParam(required = false) Long serviceConsumerId,
			@RequestParam(required = false) Long logicalAddressId,
			@RequestParam(required = false) Long serviceContractId,
			@RequestParam(required = false) Long serviceProducerId) {
		log.debug("REST request to get all ConnectionPoints as xml");

		return new ConnectionPointListDTO(getAll(platform, environment, serviceConsumerId,
				logicalAddressId, serviceContractId, serviceProducerId));

	}

	/**
	 * GET /connectionPoints/:id -> get the "id" connectionPoint. Content type
	 * from Accept header
	 */
	@RequestMapping(value = { "/{id}", "/{id}.json", "/{id}.xml" }, method = RequestMethod.GET)
	public ConnectionPointDTO getOneAcceptHeader(@PathVariable Long id) {
		log.debug("REST request to get ConnectionPoint : {}", id);

		ConnectionPoint cp = connectionPointService.find(id);
		if (cp == null) {
			log.debug("Connection point with id {} not found", id);
			throw new ResourceNotFoundException("Connection point with id " + id + " not found");
		}
		return toDTO(cp);
	}

	private List<ConnectionPointDTO> getAll(String platform, String environment,
			Long serviceConsumerId, Long logicalAddressId, Long serviceContractId,
			Long serviceProducerId) {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(environment, platform,
				serviceConsumerId, logicalAddressId, serviceContractId, serviceProducerId);
		List<ConnectionPoint> connectionPoints = connectionPointService.findAll(criteria);

		List<ConnectionPointDTO> result = new ArrayList<>();
		for (ConnectionPoint cp : connectionPoints) {
			result.add(toDTO(cp));
		}
		return result;
	}

	private ConnectionPointDTO toDTO(ConnectionPoint cp) {
		return mapper.map(cp, ConnectionPointDTO.class);
	}

}
