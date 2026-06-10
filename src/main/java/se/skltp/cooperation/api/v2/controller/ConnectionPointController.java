/**
 * Copyright (c) 2015-2026 Inera.
 * * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.v2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.service.ConnectionPointCriteria;
import se.skltp.cooperation.service.ConnectionPointService;
import se.skltp.cooperation.api.exception.ResourceNotFoundException;
import se.skltp.cooperation.api.v2.dto.ConnectionPointDTO;
import se.skltp.cooperation.api.v2.listdto.ConnectionPointListDTO;

/**
 * REST controller for managing ConnectionPoint.
 *
 */
@RestController
@RequestMapping(value = {
	"/api/v2/connectionPoints",
	"/api/v2/connectionPoints/",
	"/api/v2/connectionPoints.json",
	"/api/v2/connectionPoints.xml"
})
public class ConnectionPointController {

	private final Logger log = LoggerFactory.getLogger(ConnectionPointController.class);

	private final ConnectionPointService connectionPointService;
	private final ModelMapper mapper;
	private final Environment environment;

	@Autowired
	public ConnectionPointController(ConnectionPointService connectionPointService,
			ModelMapper mapper,
			Environment environment) {
		this.connectionPointService = connectionPointService;
		this.mapper = mapper;
		this.environment = environment;
	}

	/**
	 * GET /connectionPoints -> get all the connectionPoints as json
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ConnectionPointDTO> getAllAsJson(@RequestParam(required = false) String platform,
			@RequestParam(required = false) String environment,
			@RequestParam(required = false) Long serviceConsumerId,
			@RequestParam(required = false) Long logicalAddressId,
			@RequestParam(required = false) Long serviceContractId,
			@RequestParam(required = false) Long serviceProducerId) {
		log.debug("REST request to get all ConnectionPoints as json");

		List<ConnectionPointDTO> result = getAll(platform, environment, serviceConsumerId,
				logicalAddressId, serviceContractId, serviceProducerId);
		if (!isDevProfileActive()) {
			try {
				TimeUnit.SECONDS.sleep(91);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				log.warn("Interrupted while delaying getAllAsJson response", e);
			}
		}
		return result;

	}

	/**
	 * GET /connectionPoints -> get all the connectionPoints as xml
	 */
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
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
	@GetMapping(value = { "/{id}", "/{id}.json", "/{id}.xml" })
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

	private boolean isDevProfileActive() {
		for (String profile : environment.getActiveProfiles()) {
			if ("dev".equalsIgnoreCase(profile)) {
				return true;
			}
		}
		return false;
	}

}
