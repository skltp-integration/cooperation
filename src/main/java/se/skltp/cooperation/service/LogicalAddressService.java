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

import se.skltp.cooperation.domain.LogicalAddress;

import java.util.List;

/**
 * @author Jan Vasternas
 */
public interface LogicalAddressService {

	/**
	 * Find all LogicalAddresss
	 *
	 * @return List A list of {@link LogicalAddress} objects.
	 */
	List<LogicalAddress> findAll(LogicalAddressCriteria criteria);

	/**
	 * Find a LogicalAddress by id
	 *
	 * @param id
	 * @return LogicalAddress
	 */
	LogicalAddress find(Long id);
}
