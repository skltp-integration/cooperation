/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.service;

/**
 * A builder object for CooperationCriteria
 *
 */
public class CooperationCriteriaBuilder {

	private CooperationCriteria object = new CooperationCriteria();

	public CooperationCriteria build() {
		return object;
	}

	public CooperationCriteriaBuilder connectionPointId(Long id) {
		object.setConnectionPointId(id);
		return this;
	}

	public CooperationCriteriaBuilder logicalAddressId(Long id) {
		object.setLogicalAddressId(id);
		return this;
	}

	public CooperationCriteriaBuilder serviceConsumerId(Long id) {
		object.setServiceConsumerId(id);
		return this;
	}

	public CooperationCriteriaBuilder serviceContractId(Long id) {
		object.setServiceContractId(id);
		return this;
	}
}
