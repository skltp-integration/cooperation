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

import se.skltp.cooperation.domain.QLogicalAddress;
import se.skltp.cooperation.domain.QServiceProducer;
import se.skltp.cooperation.domain.ServiceProducer;
import se.skltp.cooperation.repository.ServiceProducerRepository;
import se.skltp.cooperation.service.ServiceProducerCriteria;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
import se.skltp.cooperation.util.ControllerUtils;

/**
 * @author Jan Vasternas
 */
@Service
@Transactional(readOnly = true)
public class ServiceProducerServiceImpl implements
		se.skltp.cooperation.service.ServiceProducerService {

	private final ServiceProducerRepository serviceProducerRepository;

	@Autowired
	public ServiceProducerServiceImpl(ServiceProducerRepository serviceProducerRepository) {
		this.serviceProducerRepository = serviceProducerRepository;
	}

	@Override
	public List<ServiceProducer> findAll(ServiceProducerCriteria criteria) {
		if (criteria.isEmpty()) {
			return serviceProducerRepository.findAll();
		} else {
			Predicate predicate = buildPredicate(criteria);
			return ControllerUtils.iterableToArrayList(serviceProducerRepository.findAll(predicate));
		}
	}

	@Override
	public ServiceProducer find(Long id) {
		return serviceProducerRepository.findById(id).orElse(null);
	}

	Predicate buildPredicate(ServiceProducerCriteria criteria) {
		BooleanBuilder builder = new BooleanBuilder();
		if (criteria.getHsaId() != null) {
			builder.and(QServiceProducer.serviceProducer.hsaId.eq(criteria.getHsaId()));
		}
		if (criteria.getServiceContractId() != null) {
			builder.and(QServiceProducer.serviceProducer.serviceProductions.any().serviceContract.id
					.eq(criteria.getServiceContractId()));
		}
		if (criteria.getLogicalAddressId() != null) {
			builder.and(QServiceProducer.serviceProducer.serviceProductions.any().logicalAddress.id
					.eq(criteria.getLogicalAddressId()));
		}
		if (criteria.getConnectionPointId() != null) {
			builder.and(QServiceProducer.serviceProducer.connectionPoint.id
					.eq(criteria.getConnectionPointId()));
		}
		if (criteria.getServiceConsumerId() != null) {
			builder.and(QServiceProducer.serviceProducer.serviceProductions.any().logicalAddress.id.in(
					JPAExpressions
					.select(QLogicalAddress.logicalAddress1.id)
					.from(QLogicalAddress.logicalAddress1)
					.where(QLogicalAddress.logicalAddress1.cooperations.any().serviceConsumer.id
							.eq(criteria.getServiceConsumerId()))
					));
		}
		return builder.getValue();
	}

}
