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
import org.springframework.web.bind.annotation.RestController;

import se.skltp.cooperation.domain.ConnectionPoint;
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
@RequestMapping("/v1/connectionPoints")
public class ConnectionPointController {

	private final Logger log = LoggerFactory
			.getLogger(ConnectionPointController.class);

	private final ConnectionPointService connectionPointService;
	private final DozerBeanMapper mapper;

	@Autowired
	public ConnectionPointController(
			ConnectionPointService connectionPointService,
			DozerBeanMapper mapper) {
		this.connectionPointService = connectionPointService;
		this.mapper = mapper;
	}

	/**
	 * GET /connectionPoints -> get all the connectionPoints. Content type: JSON
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ConnectionPointDTO> getAllAsJson() {
		log.debug("REST request to get all ConnectionPoints");
		
		List<ConnectionPointDTO> result = new ArrayList<>();
		List<ConnectionPoint> connectionPoints = connectionPointService
				.findAll();
		for (ConnectionPoint cp : connectionPoints) {
			result.add(toDTO(cp));
		}
		return result;

	}

	/**
	 * GET /connectionPoints -> get all the connectionPoints. Content type: XML
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public ConnectionPointListDTO getAllAsXml() {
		log.debug("REST request to get all ConnectionPoints");

		ConnectionPointListDTO result = new ConnectionPointListDTO();
		result.setConnectionPoints(getAllAsJson());
		return result;

	}

	/**
	 * GET /connectionPoints/:id -> get the "id" connectionPoint.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ConnectionPointDTO get(@PathVariable Long id) {
		log.debug("REST request to get ConnectionPoint : {}", id);

		ConnectionPoint cp = connectionPointService.find(id);
		if (cp == null) {
			log.debug("Connection point with id {} not found", id);
			throw new ResourceNotFoundException("Connection point with id "
					+ id + " not found");
		}
		return toDTO(cp);
	}

	private ConnectionPointDTO toDTO(ConnectionPoint cp) {
		return mapper.map(cp, ConnectionPointDTO.class);
	}

}
