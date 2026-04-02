/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
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
