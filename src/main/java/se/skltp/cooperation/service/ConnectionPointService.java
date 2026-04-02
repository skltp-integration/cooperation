/**
 * Copyright (c) 2015-2026 Inera.
 * * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.service;

import java.util.List;

import se.skltp.cooperation.domain.ConnectionPoint;

/**
 */
public interface ConnectionPointService {

	/**
	 * Find all ConnectionPoints
	 *
	 * @param criteria
	 *
	 * @return List A list of {@link ConnectionPoint} objects.
	 */
	List<ConnectionPoint> findAll(ConnectionPointCriteria criteria);

	/**
	 * Find a ConnectionPoint by id
	 *
	 * @param id
	 * @return ConnectionPoint
	 */
	ConnectionPoint find(Long id);
}
