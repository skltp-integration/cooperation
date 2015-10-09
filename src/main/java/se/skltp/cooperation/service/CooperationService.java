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
package se.skltp.cooperation.service;

import se.skltp.cooperation.domain.Cooperation;

import java.util.List;

/**
 * @author Peter Merikan
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
