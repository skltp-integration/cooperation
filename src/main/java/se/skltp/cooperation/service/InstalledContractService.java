/**
 * Copyright (c) 2015-2026 Inera.
 * * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.service;

import java.util.List;

import se.skltp.cooperation.domain.InstalledContract;

/**
 * @author Jan Vasternas
 */
public interface InstalledContractService {

	/**
	 * Find all ServiceContracts
	 *
	 * @return List A list of {@link InstalledContract} objects.
	 */
	List<InstalledContract> findAll(InstalledContractCriteria criteria);

	/**
	 * Find a ServiceContract by id
	 *
	 * @param id
	 * @return InstalledContract
	 */
	InstalledContract find(Long id);
}
