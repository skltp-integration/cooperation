/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.service;

import se.skltp.cooperation.domain.ServiceContract;

import java.util.List;

/**
 * @author Jan Vasternas
 */
public interface ServiceContractService {

	/**
	 * Find all ServiceContracts
	 *
	 * @return List A list of {@link ServiceContract} objects.
	 */
	List<ServiceContract> findAll(ServiceContractCriteria criteria);

	/**
	 * Find a ServiceContract by id
	 *
	 * @param id
	 * @return ServiceContract
	 */
	ServiceContract find(Long id);
}
