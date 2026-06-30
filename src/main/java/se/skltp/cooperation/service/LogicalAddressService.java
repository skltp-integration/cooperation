/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
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
