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

import se.skltp.cooperation.domain.Cooperation;
import se.skltp.cooperation.service.CooperationCriteria;
import se.skltp.cooperation.service.CooperationService;
import se.skltp.cooperation.web.rest.exception.ResourceNotFoundException;
import se.skltp.cooperation.web.rest.v1.dto.CooperationDTO;
import se.skltp.cooperation.web.rest.v1.listdto.CooperationListDTO;

import com.google.common.base.Splitter;

/**
 * REST controller to handle resource Cooperation
 */
@RestController
@RequestMapping(value = { "/api/v1/cooperations", "/api/v1/cooperations.json",
		"/api/v1/cooperations.xml" })
public class CooperationController {

	private static final Splitter SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();
	public static String INCLUDE_SERVICECONSUMER = "serviceConsumer";
	public static String INCLUDE_SERVICECONTRACT = "serviceContract";
	public static String INCLUDE_CONNECTIONPOINT = "connectionPoint";
	public static String INCLUDE_LOGICALADDRESS = "logicalAddress";

	private final Logger log = LoggerFactory.getLogger(CooperationController.class);
	private final CooperationService cooperationService;
	private final DozerBeanMapper mapper;

	@Autowired
	public CooperationController(CooperationService cooperationService, DozerBeanMapper mapper) {
		this.cooperationService = cooperationService;
		this.mapper = mapper;
	}

	/**
	 * GET /cooperations -> get all the cooperations. Content type: JSON
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CooperationDTO> getAllAsJson(
			@RequestParam(required = false) Long serviceConsumerId,
			@RequestParam(required = false) Long logicalAddressId,
			@RequestParam(required = false) Long serviceContractId,
			@RequestParam(required = false) Long connectionPointId,
			@RequestParam(required = false) String include) {
		log.debug("REST request to get all Cooperations as json");
		
		return getAll(serviceConsumerId, logicalAddressId, serviceContractId, connectionPointId,
				include);

	}


	/**
	 * GET /cooperations -> get all cooperations. Content type: XML
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public CooperationListDTO getAllAsXml(@RequestParam(required = false) Long serviceConsumerId,
			@RequestParam(required = false) Long logicalAddressId,
			@RequestParam(required = false) Long serviceContractId,
			@RequestParam(required = false) Long connectionPointId,
			@RequestParam(required = false) String include) {
		log.debug("REST request to get all Cooperations as xml");

		CooperationListDTO result = new CooperationListDTO();
		result.setCooperations(getAll(serviceConsumerId, logicalAddressId, serviceContractId,
				connectionPointId, include));
		return result;

	}

	/**
	 * GET /cooperations/:id -> get the "id" cooperation.
	 */
	@RequestMapping(value = { "/{id}", "/{id}.json", "/{id}.xml" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public CooperationDTO get(@PathVariable Long id) {
		log.debug("REST request to get ConnectionPoint : {}", id);

		Cooperation coop = cooperationService.find(id);
		if (coop == null) {
			log.debug("Cooperation with id {} not found", id);
			throw new ResourceNotFoundException("Cooperation with id " + id + " not found");
		}
		return toDTO(coop);
	}
	
	private List<CooperationDTO> getAll(Long serviceConsumerId, Long logicalAddressId,
			Long serviceContractId, Long connectionPointId, String include) {

		List<String> includes = (include != null) ? SPLITTER.splitToList(include)
				: new ArrayList<>();

		CooperationCriteria criteria = new CooperationCriteria(serviceConsumerId, logicalAddressId,
				serviceContractId, connectionPointId);
		List<Cooperation> cooperations = cooperationService.findAll(criteria);

		List<CooperationDTO> result = new ArrayList<>();
		for (Cooperation cp : cooperations) {
			includeOrNot(includes, cp);
			result.add(toDTO(cp));
		}
		return result;
	}

	/**
	 * Decide which compound objects that should be included. If not requested
	 * to be included it will be nulled.
	 *
	 * @param includes
	 *            A list of parameter values
	 * @param cooperation
	 */
	void includeOrNot(List<String> includes, Cooperation cooperation) {
		if (includes != null) {
			if (!includes.contains(INCLUDE_CONNECTIONPOINT))
				cooperation.setConnectionPoint(null);
			if (!includes.contains(INCLUDE_LOGICALADDRESS))
				cooperation.setLogicalAddress(null);
			if (!includes.contains(INCLUDE_SERVICECONSUMER))
				cooperation.setServiceConsumer(null);
			if (!includes.contains(INCLUDE_SERVICECONTRACT))
				cooperation.setServiceContract(null);
		}
	}

	private CooperationDTO toDTO(Cooperation coop) {
		return mapper.map(coop, CooperationDTO.class);
	}

}
