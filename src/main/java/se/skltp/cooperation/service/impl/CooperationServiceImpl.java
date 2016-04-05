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
package se.skltp.cooperation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.skltp.cooperation.domain.Cooperation;
import se.skltp.cooperation.domain.QCooperation;
import se.skltp.cooperation.repository.CooperationRepository;
import se.skltp.cooperation.service.CooperationCriteria;
import se.skltp.cooperation.service.CooperationService;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

/**
 * @author Peter Merikan
 */
@Service
@Transactional(readOnly = true)
public class CooperationServiceImpl implements CooperationService {

	private final CooperationRepository cooperationRepository;

	@Autowired
	public CooperationServiceImpl(CooperationRepository cooperationRepository) {
		this.cooperationRepository = cooperationRepository;
	}

	@Override
	public List<Cooperation> findAll(CooperationCriteria criteria) {

		if (criteria.isEmpty()) {
			return cooperationRepository.findAll();
		} else {
			Predicate predicate = buildPredicate(criteria);
			return Lists.newArrayList(cooperationRepository.findAll(predicate));
		}
	}

	@Override
	public Cooperation find(Long id) {
		return cooperationRepository.findOne(id);
	}

	Predicate buildPredicate(CooperationCriteria criteria) {
		BooleanBuilder builder = new BooleanBuilder();
		if (criteria.getServiceConsumerId() != null) {
			builder.and(QCooperation.cooperation.serviceConsumer.id.eq(criteria.getServiceConsumerId()));
		}
		if (criteria.getLogicalAddressId() != null) {
			builder.and(QCooperation.cooperation.logicalAddress.id.eq(criteria.getLogicalAddressId()));
		}
		if (criteria.getServiceContractId() != null) {
			builder.and(QCooperation.cooperation.serviceContract.id.eq(criteria.getServiceContractId()));
		}
		if (criteria.getConnectionPointId() != null) {
			builder.and(QCooperation.cooperation.connectionPoint.id.eq(criteria.getConnectionPointId()));
		}
		if (criteria.getServiceDomainId() != null) {
			builder.and(QCooperation.cooperation.serviceContract.serviceDomain.id.eq(criteria.getServiceDomainId()));
		}
		return builder.getValue();
	}

}
