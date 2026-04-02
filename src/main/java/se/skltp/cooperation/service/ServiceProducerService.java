/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.service;

import java.util.List;

import se.skltp.cooperation.domain.ServiceProducer;

/**
 * @author Jan Vasternas
 */
public interface ServiceProducerService {

	/**
	 * Find all ServiceProducers
	 * @param criteria
	 *
	 * @return List A list of {@link ServiceProducer} objects.
	 */
	List<ServiceProducer> findAll(ServiceProducerCriteria criteria);

	/**
	 * Find a ServiceProducer by id
	 *
	 * @param id
	 * @return ConnectionPoint
	 */
	ServiceProducer find(Long id);
}
