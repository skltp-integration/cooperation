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

import se.skltp.cooperation.domain.ServiceProduction;
import se.skltp.cooperation.service.ServiceProductionCriteria;
import se.skltp.cooperation.service.ServiceProductionService;
import se.skltp.cooperation.api.exception.ResourceNotFoundException;
import se.skltp.cooperation.api.v2.dto.ServiceProductionDTO;
import se.skltp.cooperation.api.v2.format.HTTPObfuscator;
import se.skltp.cooperation.api.v2.listdto.ServiceProductionListDTO;

import com.google.common.base.Splitter;

/**
 * REST controller to handle resource ServiceProduction
 *
 * @author Jan Vasternas
 */
@RestController
@RequestMapping(value = {
	"/api/v2/serviceProductions",
	"/api/v2/serviceProductions/",
	"/api/v2/serviceProductions.json",
	"/api/v2/serviceProductions.xml"
})
public class ServiceProductionController {

	private static final Splitter SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();
	private static final String INCLUDE_PHYSICAL_ADDRESS = "physicalAddress";
	public static final String INCLUDE_SERVICEPRODUCER = "serviceProducer";
	public static final String INCLUDE_SERVICECONTRACT = "serviceContract";
	public static final String INCLUDE_CONNECTIONPOINT = "connectionPoint";
	public static final String INCLUDE_LOGICALADDRESS = "logicalAddress";

	private final Logger log = LoggerFactory.getLogger(ServiceProductionController.class);

	@Autowired
	private ServiceProductionService serviceProductionService;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	HTTPObfuscator httpObfuscator;

	/**
	 * GET /serviceProductions -> get all the serviceProductions. Content type:
	 * JSON
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ServiceProductionDTO> getAllAsJson(
			@RequestParam(required = false) String physicalAddress,
			@RequestParam(required = false) String rivtaProfile,
			@RequestParam(required = false) Long serviceProducerId,
			@RequestParam(required = false) Long logicalAddressId,
			@RequestParam(required = false) Long serviceContractId,
			@RequestParam(required = false) Long connectionPointId,
			@RequestParam(required = false) Long serviceDomainId,
			@RequestParam(required = false) String include) {
		log.debug("REST request to get all ServiceProductions as json");

		return getAll(physicalAddress, rivtaProfile, serviceProducerId, logicalAddressId,
				serviceContractId, connectionPointId, serviceDomainId, include);

	}

	/**
	 * GET /serviceProductions -> get all serviceProductions. Content type: XML
	 */
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public ServiceProductionListDTO getAllAsXml(
			@RequestParam(required = false) String physicalAddress,
			@RequestParam(required = false) String rivtaProfile,
			@RequestParam(required = false) Long serviceProducerId,
			@RequestParam(required = false) Long logicalAddressId,
			@RequestParam(required = false) Long serviceContractId,
			@RequestParam(required = false) Long connectionPointId,
			@RequestParam(required = false) Long serviceDomainId,
			@RequestParam(required = false) String include) {
		log.debug("REST request to get all ServiceProductions as xml");

		ServiceProductionListDTO result = new ServiceProductionListDTO();
		result.setServiceProductions(getAll(physicalAddress, rivtaProfile, serviceProducerId,
				logicalAddressId, serviceContractId, connectionPointId, serviceDomainId, include));
		return result;

	}

	/**
	 * GET /serviceProductions/:id -> get the "id" serviceProduction.
	 */
	@GetMapping(value = { "/{id}", "/{id}.json", "/{id}.xml" }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ServiceProductionDTO get(@PathVariable Long id,
			@RequestParam(required = false) String include) {
		log.debug("REST request to get ConnectionPoint : {}", id);

		ServiceProduction serviceProduction = serviceProductionService.find(id);
		if (serviceProduction == null) {
			log.debug("ServiceProduction with id {} not found", id);
			throw new ResourceNotFoundException("ServiceProduction with id " + id + " not found");
		}
		if (!INCLUDE_PHYSICAL_ADDRESS.equals(include)) {
			serviceProduction.setPhysicalAddress(httpObfuscator.obfuscate(serviceProduction
					.getPhysicalAddress()));
		}
		return toDTO(serviceProduction);
	}

	private List<ServiceProductionDTO> getAll(String physicalAddress, String rivtaProfile,
			Long serviceProducerId, Long logicalAddressId, Long serviceContractId,
			Long connectionPointId, Long serviceDomainId, String include) {

		List<String> includes = new ArrayList<>();
		if (include != null) {
			includes.addAll(SPLITTER.splitToList(include));
		}

		ServiceProductionCriteria criteria = new ServiceProductionCriteria(physicalAddress,
				rivtaProfile, serviceProducerId, logicalAddressId, serviceContractId,
				connectionPointId, serviceDomainId);
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
		if (!includes.contains(INCLUDE_CONNECTIONPOINT)){
			serviceProduction.setConnectionPoint(null);
		}
		if (!includes.contains(INCLUDE_LOGICALADDRESS)){
			serviceProduction.setLogicalAddress(null);
		}
		if (!includes.contains(INCLUDE_SERVICEPRODUCER)){
			serviceProduction.setServiceProducer(null);
		} else {
			serviceProduction.getServiceProducer().setConnectionPoint(null);
		}
		if (!includes.contains(INCLUDE_SERVICECONTRACT)){
			serviceProduction.setServiceContract(null);
		}
		// Secret parameter to show complete physicalAddress
		if (!includes.contains(INCLUDE_PHYSICAL_ADDRESS))
			serviceProduction.setPhysicalAddress(httpObfuscator.obfuscate(serviceProduction
					.getPhysicalAddress()));
	}

	private ServiceProductionDTO toDTO(ServiceProduction coop) {
		return mapper.map(coop, ServiceProductionDTO.class);
	}

}
