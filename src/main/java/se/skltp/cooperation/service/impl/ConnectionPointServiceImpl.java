/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.QConnectionPoint;
import se.skltp.cooperation.repository.ConnectionPointRepository;
import se.skltp.cooperation.service.ConnectionPointCriteria;
import se.skltp.cooperation.service.ConnectionPointService;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import se.skltp.cooperation.util.ControllerUtils;

/**
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
			return ControllerUtils.iterableToArrayList(connectionPointRepository.findAll(predicate));
		}

	}

	@Override
	public ConnectionPoint find(Long id) {
		return connectionPointRepository.findById(id).orElse(null);
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
