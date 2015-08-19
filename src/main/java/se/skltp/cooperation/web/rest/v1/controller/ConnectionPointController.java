package se.skltp.cooperation.web.rest.v1.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
public class ConnectionPointController {

	private final Logger log = LoggerFactory.getLogger(ConnectionPointController.class);

	private final ConnectionPointService connectionPointService;
	private final DozerBeanMapper mapper;

	@Autowired
	public ConnectionPointController(ConnectionPointService connectionPointService, DozerBeanMapper mapper) {
		this.connectionPointService = connectionPointService;
		this.mapper = mapper;
	}

	/**
	 * GET /connectionPoints -> get all the connectionPoints. Accept-header: json
	 * JSON
	 */
	@RequestMapping(value = "/api/v1/connectionPoints", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ConnectionPointDTO> getAllAcceptJson() {
		log.debug("REST request to get all ConnectionPoints Accept=json");

		return getAll();

	}

	/**
	 * GET /connectionPoints -> get all the connectionPoints. URL: json
	 */
	@RequestMapping(value = "/api/v1/connectionPoints.json", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<ConnectionPointDTO> getAllJsonUrl(HttpServletRequest request) {
		log.debug("REST request to get all ConnectionPoints URL=json");

		return getAll();

	}

	/**
	 * GET /connectionPoints -> get all the connectionPoints. Accept-header: xml
	 */
	@RequestMapping(value = "/api/v1/connectionPoints", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public ConnectionPointListDTO getAllAcceptXml() {
		log.debug("REST request to get all ConnectionPoints Accept=xml");

		// Varför fungerar detta ? Är det pga av XML-notationen i ListDTOen 
		return new ConnectionPointListDTO(getAll());

	}

	/**
	 * GET /connectionPoints -> get all the connectionPoints. URL: xml
	 */
	@RequestMapping(value = "/api/v1/connectionPoints.xml", method = RequestMethod.GET)
	public ConnectionPointListDTO getAllXmlUrl(HttpServletRequest request) {
		log.debug("REST request to get all ConnectionPoints URL=xml");
		
		// Varför fungerar detta ? Är det pga av XML-notationen i ListDTOen ?
		return new ConnectionPointListDTO(getAll());

	}

	private List<ConnectionPointDTO> getAll() {
		List<ConnectionPointDTO> result = new ArrayList<>();
		List<ConnectionPoint> connectionPoints = connectionPointService.findAll();
		for (ConnectionPoint cp : connectionPoints) {
			result.add(toDTO(cp));
		}
		return result;
	}

	/**
	 * GET /connectionPoints/:id -> get the "id" connectionPoint. Content type from Accept header
	 */
	@RequestMapping(value = "/api/v1/connectionPoints/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ConnectionPointDTO getOneAcceptHeader(@PathVariable Long id) {
		log.debug("REST request to get ConnectionPoint : {} Accept header", id);

		return getOne(id);
	}
	/**
	 * GET /connectionPoints/:id -> get the "id" connectionPoint. Content type from Accept header
	 */
	@RequestMapping(value = "/api/v1/connectionPoints.json/{id}", method = RequestMethod.GET)
	public ConnectionPointDTO getOneJsonUrl(@PathVariable Long id) {
		log.debug("REST request to get ConnectionPoint : {} Url=json", id);

		return getOne(id);
	}
	/**
	 * GET /connectionPoints/:id -> get the "id" connectionPoint. Content type from Accept header
	 */
	@RequestMapping(value = "/api/v1/connectionPoints.xml/{id}", method = RequestMethod.GET)
	public ConnectionPointDTO getOneXmlUrl(@PathVariable Long id) {
		log.debug("REST request to get ConnectionPoint : {} Url=xml", id);

		return getOne(id);
	}

	private ConnectionPointDTO getOne(Long id) {
		ConnectionPoint cp = connectionPointService.find(id);
		if (cp == null) {
			log.debug("Connection point with id {} not found", id);
			throw new ResourceNotFoundException("Connection point with id " + id + " not found");
		}
		return toDTO(cp);
	}

	private ConnectionPointDTO toDTO(ConnectionPoint cp) {
		return mapper.map(cp, ConnectionPointDTO.class);
	}

}
