/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
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
