/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.service;

import java.util.List;

import se.skltp.cooperation.domain.ServiceProduction;

/**
 */
public interface ServiceProductionService {

	/**
	 * Find all ServiceProductions by given criteria
	 *
	 * @param criteria
	 * @return List A list of {@link ServiceProduction} objects.
	 */
	List<ServiceProduction> findAll(ServiceProductionCriteria criteria);

	/**
	 * Find a cooperation by id
	 *
	 * @param id
	 * @return ServiceProduction
	 */
	ServiceProduction find(Long id);
}
