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

import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.QConnectionPoint;
import se.skltp.cooperation.repository.ConnectionPointRepository;
import se.skltp.cooperation.service.ConnectionPointCriteria;
import se.skltp.cooperation.service.ConnectionPointService;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

/**
 * @author Peter Merikan
 */
@Service
@Transactional(readOnly = true)
public class ConnectionPointServiceImpl implements ConnectionPointService {

	private final ConnectionPointRepository connectionPointRepository;

	@Autowired
	public ConnectionPointServiceImpl(ConnectionPointRepository connectionPointRepository) {
		this.connectionPointRepository = connectionPointRepository;
	}

	@Override
	public List<ConnectionPoint> findAll(ConnectionPointCriteria criteria) {

		if (criteria.isEmpty()) {
			return connectionPointRepository.findAll();
		} else {
			Predicate predicate = buildPredicate(criteria);
			return Lists.newArrayList(connectionPointRepository.findAll(predicate));
		}

	}

	@Override
	public ConnectionPoint find(Long id) {
		return connectionPointRepository.findOne(id);
	}

	Predicate buildPredicate(ConnectionPointCriteria criteria) {
		BooleanBuilder builder = new BooleanBuilder();
		if (criteria.getPlatform() != null) {
			builder.and(QConnectionPoint.connectionPoint.platform.eq(criteria.getPlatform()));
		}
		if (criteria.getEnvironment() != null) {
			builder.and(QConnectionPoint.connectionPoint.environment.eq(criteria.getEnvironment()));
		}
		if (criteria.getServiceConsumerId() != null) {
			builder.and(QConnectionPoint.connectionPoint.serviceConsumers.any().id
					.eq(criteria.getServiceConsumerId()));
		}
		if (criteria.getLogicalAddressId() != null) {
			builder.and(QConnectionPoint.connectionPoint.serviceProductions.any().logicalAddress.id
					.eq(criteria.getLogicalAddressId()));
		}
		if (criteria.getServiceContractId() != null) {
			builder.and(QConnectionPoint.connectionPoint.serviceProductions.any().serviceContract.id
					.eq(criteria.getServiceContractId()));
		}
		if (criteria.getServiceProducerId() != null) {
			builder.and(QConnectionPoint.connectionPoint.serviceProducers.any().id
					.eq(criteria.getServiceProducerId()));
		}
		return builder.getValue();
	}

}
