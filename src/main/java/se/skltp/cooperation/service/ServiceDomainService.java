/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.service;

import java.util.List;

import se.skltp.cooperation.domain.ServiceDomain;

/**
 * @author Jan Vasternas
 */
public interface ServiceDomainService {

	/**
	 * Find all ServiceDomains
	 *
	 * @param criteria
	 *
	 * @return List A list of {@link ServiceDomain} objects.
	 */
	List<ServiceDomain> findAll(ServiceDomainCriteria criteria);

	/**
	 * Find a ServiceDomain by id
	 *
	 * @param id
	 * @return ServiceDomain
	 */
	ServiceDomain find(Long id);
}
