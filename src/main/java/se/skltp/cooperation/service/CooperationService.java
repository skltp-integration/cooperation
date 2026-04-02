/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.service;

import se.skltp.cooperation.domain.Cooperation;

import java.util.List;

/**
 */
public interface CooperationService {

	/**
	 * Find all Cooperations by given criteria
	 *
	 * @param criteria
	 * @return List A list of {@link Cooperation} objects.
	 */
	List<Cooperation> findAll(CooperationCriteria criteria);

	/**
	 * Find a cooperation by id
	 *
	 * @param id
	 * @return Cooperation
	 */
	Cooperation find(Long id);
}
