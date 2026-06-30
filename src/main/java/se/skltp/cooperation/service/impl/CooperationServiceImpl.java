/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
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

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import se.skltp.cooperation.util.ControllerUtils;

/**
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
			return ControllerUtils.iterableToArrayList(cooperationRepository.findAll(predicate));
		}
	}

	@Override
	public Cooperation find(Long id) {
		return cooperationRepository.findById(id).orElse(null);
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
