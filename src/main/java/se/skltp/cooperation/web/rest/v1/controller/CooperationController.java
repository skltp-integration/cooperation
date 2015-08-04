package se.skltp.cooperation.web.rest.v1.controller;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.skltp.cooperation.domain.Cooperation;
import se.skltp.cooperation.repository.CooperationPredicates;
import se.skltp.cooperation.repository.CooperationRepository;
import se.skltp.cooperation.web.rest.v1.dto.cooperation.CooperationDTO;
import se.skltp.cooperation.web.rest.v1.dto.cooperation.CooperationListDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller to handle resource Cooperation
 */
@RestController
@RequestMapping("/v1/cooperations")
public class CooperationController {

	private static final Splitter SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();
	public static String INCLUDE_SERVICECONSUMER = "serviceConsumer";
	public static String INCLUDE_SERVICECONTRACT = "serviceContract";
	public static String INCLUDE_CONNECTIONPOINT = "connectionPoint";
	public static String INCLUDE_LOGICALADDRESS = "logicalAddress";
	private final Logger log = LoggerFactory.getLogger(CooperationController.class);
	@Autowired
	private CooperationRepository cooperationRepository;
	@Autowired
	private DozerBeanMapper mapper;

	/**
	 * GET  /cooperations -> get all the cooperations.
	 * Content type: JSON
	 */
	@RequestMapping(method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CooperationDTO> getAllAsJson(
		@RequestParam(required = false) Long serviceConsumerId,
		@RequestParam(required = false) Long logicalAddressId,
		@RequestParam(required = false) Long serviceContractId,
		@RequestParam(required = false) Long connectionPointId,
		@RequestParam(required = false) String include) {

		log.debug("REST request to get all Cooperations as {}", MediaType.APPLICATION_JSON_VALUE);
		List<CooperationDTO> result = new ArrayList<>();

		List<String> includes = (include != null) ? SPLITTER.splitToList(include) : new ArrayList<>();

		List<Cooperation> cooperations;
		Predicate criteria = buildCriteria(serviceConsumerId, logicalAddressId, serviceContractId, connectionPointId);
		if (criteria != null) {
			cooperations = Lists.newArrayList(cooperationRepository.findAll(criteria));
		} else {
			cooperations = cooperationRepository.findAll();
		}

		for (Cooperation cp : cooperations) {
			includeOrNot(includes, cp);
			result.add(mapper.map(cp, CooperationDTO.class));
		}
		return result;

	}

	/**
	 * GET  /cooperations -> get all cooperations.
	 * Content type: XML
	 */
	@RequestMapping(method = RequestMethod.GET,
		produces = MediaType.APPLICATION_XML_VALUE)
	public CooperationListDTO getAllAsXml(
		@RequestParam(required = false) Long serviceConsumerId,
		@RequestParam(required = false) Long logicalAddressId,
		@RequestParam(required = false) Long serviceContractId,
		@RequestParam(required = false) Long connectionPointId,
		@RequestParam(required = false) String include) {
		log.debug("REST request to get all Cooperations as {}", MediaType.APPLICATION_XML);

		CooperationListDTO result = new CooperationListDTO();
		result.setCooperations(getAllAsJson(serviceConsumerId, logicalAddressId, serviceContractId, connectionPointId, include));

		return result;

	}

	/**
	 * GET  /cooperations/:id -> get the "id" cooperation.
	 */
	@RequestMapping(value = "/{id}",
		method = RequestMethod.GET,
		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<CooperationDTO> get(@PathVariable Long id) {
		log.debug("REST request to get ConnectionPoint : {}", id);
		return Optional.ofNullable(cooperationRepository.findOne(id))
			.map(cooperation -> new ResponseEntity<>(
				mapper.map(cooperation, CooperationDTO.class),
				HttpStatus.OK))
			.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	Predicate buildCriteria(Long serviceConsumerId, Long logicalAddressId, Long serviceContractId, Long connectionPointId) {
		log.debug("buildCriteria(serviceConsumerId:{}, logicalAddressId:{}, serviceContractId:{}, connectionPointId:{})", serviceConsumerId, logicalAddressId, serviceContractId, connectionPointId);

		BooleanBuilder builder = new BooleanBuilder();
		if (serviceConsumerId != null) {
			builder.and(CooperationPredicates.serviceConsumerIdIs(serviceConsumerId));
		}
		if (logicalAddressId != null) {
			builder.and(CooperationPredicates.logicalAddressIdIs(logicalAddressId));
		}
		if (serviceContractId != null) {
			builder.and(CooperationPredicates.serviceContractIdIs(serviceContractId));
		}
		if (connectionPointId != null) {
			builder.and(CooperationPredicates.connectionPointIdIs(connectionPointId));
		}

		return builder.hasValue() ? builder.getValue() : null;
	}

	/**
	 * Decide which compound objects that should be included
	 *
	 * @param includes    A list of parameter values
	 * @param cooperation
	 */
	void includeOrNot(List<String> includes, Cooperation cooperation) {
		if (includes != null) {
			if (!includes.contains(INCLUDE_CONNECTIONPOINT)) cooperation.setConnectionPoint(null);
			if (!includes.contains(INCLUDE_LOGICALADDRESS)) cooperation.setLogicalAddress(null);
			if (!includes.contains(INCLUDE_SERVICECONSUMER)) cooperation.setServiceConsumer(null);
			if (!includes.contains(INCLUDE_SERVICECONTRACT)) cooperation.setServiceContract(null);
		}
	}

}
