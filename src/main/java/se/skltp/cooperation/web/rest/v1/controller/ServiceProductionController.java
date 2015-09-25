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

import se.skltp.cooperation.domain.ServiceProduction;
import se.skltp.cooperation.service.ServiceProductionCriteria;
import se.skltp.cooperation.service.ServiceProductionService;
import se.skltp.cooperation.web.rest.exception.ResourceNotFoundException;
import se.skltp.cooperation.web.rest.v1.dto.ServiceProductionDTO;
import se.skltp.cooperation.web.rest.v1.listdto.ServiceProductionListDTO;

import com.google.common.base.Splitter;

/**
 * REST controller to handle resource ServiceProduction
 *
 * @author Jan Vasternas
 */
@RestController
@RequestMapping(value = { "/api/v1/serviceProductions", "/api/v1/serviceProductions.json",
		"/api/v1/serviceProductions.xml" })
public class ServiceProductionController {

	private static final Splitter SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();
	public static String INCLUDE_SERVICEPRODUCER = "serviceProducer";
	public static String INCLUDE_SERVICECONTRACT = "serviceContract";
	public static String INCLUDE_CONNECTIONPOINT = "connectionPoint";
	public static String INCLUDE_LOGICALADDRESS = "logicalAddress";

	private final Logger log = LoggerFactory.getLogger(ServiceProductionController.class);
	private final ServiceProductionService serviceProductionService;
	private final DozerBeanMapper mapper;

	@Autowired
	public ServiceProductionController(ServiceProductionService serviceProductionService,
			DozerBeanMapper mapper) {
		this.serviceProductionService = serviceProductionService;
		this.mapper = mapper;
	}

	/**
	 * GET /serviceProductions -> get all the serviceProductions. Content type:
	 * JSON
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ServiceProductionDTO> getAllAsJson(
			@RequestParam(required = false) String physicalAddress,
			@RequestParam(required = false) String rivtaProfile,
			@RequestParam(required = false) Long serviceProducerId,
			@RequestParam(required = false) Long logicalAddressId,
			@RequestParam(required = false) Long serviceContractId,
			@RequestParam(required = false) Long connectionPointId,
			@RequestParam(required = false) String include) {
		log.debug("REST request to get all ServiceProductions as json");

		return getAll(physicalAddress, rivtaProfile, serviceProducerId, logicalAddressId,
				serviceContractId, connectionPointId, include);

	}

	/**
	 * GET /serviceProductions -> get all serviceProductions. Content type: XML
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public ServiceProductionListDTO getAllAsXml(
			@RequestParam(required = false) String physicalAddress,
			@RequestParam(required = false) String rivtaProfile,
			@RequestParam(required = false) Long serviceProducerId,
			@RequestParam(required = false) Long logicalAddressId,
			@RequestParam(required = false) Long serviceContractId,
			@RequestParam(required = false) Long connectionPointId,
			@RequestParam(required = false) String include) {
		log.debug("REST request to get all ServiceProductions as xml");

		ServiceProductionListDTO result = new ServiceProductionListDTO();
		result.setServiceProductions(getAll(physicalAddress, rivtaProfile, serviceProducerId,
				logicalAddressId, serviceContractId, connectionPointId, include));
		return result;

	}

	/**
	 * GET /serviceProductions/:id -> get the "id" serviceProduction.
	 */
	@RequestMapping(value = { "/{id}", "/{id}.json", "/{id}.xml" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ServiceProductionDTO get(@PathVariable Long id) {
		log.debug("REST request to get ConnectionPoint : {}", id);

		ServiceProduction coop = serviceProductionService.find(id);
		if (coop == null) {
			log.debug("ServiceProduction with id {} not found", id);
			throw new ResourceNotFoundException("ServiceProduction with id " + id + " not found");
		}
		return toDTO(coop);
	}

	private List<ServiceProductionDTO> getAll(String physicalAddress, String rivtaProfile,
			Long serviceProducerId, Long logicalAddressId, Long serviceContractId,
			Long connectionPointId, String include) {

		List<String> includes = (include != null) ? SPLITTER.splitToList(include)
				: new ArrayList<>();

		ServiceProductionCriteria criteria = new ServiceProductionCriteria(physicalAddress,
				rivtaProfile, serviceProducerId, logicalAddressId, serviceContractId,
				connectionPointId);
		List<ServiceProduction> serviceProductions = serviceProductionService.findAll(criteria);

		List<ServiceProductionDTO> result = new ArrayList<>();
		for (ServiceProduction cp : serviceProductions) {
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
	 * @param serviceProduction
	 */
	void includeOrNot(List<String> includes, ServiceProduction serviceProduction) {
		if (includes != null) {
			if (!includes.contains(INCLUDE_CONNECTIONPOINT))
				serviceProduction.setConnectionPoint(null);
			if (!includes.contains(INCLUDE_LOGICALADDRESS))
				serviceProduction.setLogicalAddress(null);
			if (!includes.contains(INCLUDE_SERVICEPRODUCER))
				serviceProduction.setServiceProducer(null);
			if (!includes.contains(INCLUDE_SERVICECONTRACT))
				serviceProduction.setServiceContract(null);
		}
	}

	private ServiceProductionDTO toDTO(ServiceProduction coop) {
		return mapper.map(coop, ServiceProductionDTO.class);
	}

}
