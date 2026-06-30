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

import se.skltp.cooperation.domain.QServiceContract;
import se.skltp.cooperation.domain.ServiceContract;
import se.skltp.cooperation.repository.ServiceContractRepository;
import se.skltp.cooperation.service.ServiceContractCriteria;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import se.skltp.cooperation.util.ControllerUtils;

/**
 * @author Jan Vasternas
 */
@Service
@Transactional(readOnly = true)
public class ServiceContractServiceImpl implements se.skltp.cooperation.service.ServiceContractService {

	private final ServiceContractRepository serviceContractRepository;

	@Autowired
	public ServiceContractServiceImpl(ServiceContractRepository ServiceContractRepository) {
		this.serviceContractRepository = ServiceContractRepository;
	}

	@Override
	public List<ServiceContract> findAll(ServiceContractCriteria criteria) {
		if (criteria.isEmpty()) {
			return serviceContractRepository.findAll();
		} else {
			Predicate predicate = buildPredicate(criteria);
			return ControllerUtils.iterableToArrayList(serviceContractRepository.findAll(predicate));
		}
	}

	@Override
	public ServiceContract find(Long id) {
		return serviceContractRepository.findById(id).orElse(null);
	}

	Predicate buildPredicate(ServiceContractCriteria criteria) {
		BooleanBuilder builder = new BooleanBuilder();
		if (criteria.getNamespace() != null) {
			builder.and(QServiceContract.serviceContract.namespace.eq(criteria.getNamespace()));
		}
		if (criteria.getServiceConsumerId() != null) {
			builder.and(QServiceContract.serviceContract.cooperations.any().serviceConsumer.id
					.eq(criteria.getServiceConsumerId()));
		}
		if (criteria.getLogicalAddressId() != null) {
			builder.and(QServiceContract.serviceContract.serviceProductions.any().logicalAddress.id
					.eq(criteria.getLogicalAddressId()));
		}
		if (criteria.getConnectionPointId() != null) {
			builder.and(QServiceContract.serviceContract.serviceProductions.any().connectionPoint.id
					.eq(criteria.getConnectionPointId()));
		}
		if (criteria.getServiceProducerId() != null) {
			builder.and(QServiceContract.serviceContract.serviceProductions.any().serviceProducer.id
					.eq(criteria.getServiceProducerId()));
		}
		if (criteria.getServiceDomainId() != null) {
			builder.and(QServiceContract.serviceContract.serviceDomain.id
					.eq(criteria.getServiceDomainId()));
		}
		return builder.getValue();
	}

}
