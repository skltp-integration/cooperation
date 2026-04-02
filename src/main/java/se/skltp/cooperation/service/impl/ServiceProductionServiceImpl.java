/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.skltp.cooperation.domain.QServiceProduction;
import se.skltp.cooperation.domain.ServiceProduction;
import se.skltp.cooperation.repository.ServiceProductionRepository;
import se.skltp.cooperation.service.ServiceProductionCriteria;
import se.skltp.cooperation.service.ServiceProductionService;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import se.skltp.cooperation.util.ControllerUtils;

/**
 * @author Jan Vasternas
 */
@Service
@Transactional(readOnly = true)
public class ServiceProductionServiceImpl implements ServiceProductionService {

	private final ServiceProductionRepository serviceProductionRepository;

	private final Logger log = LoggerFactory.getLogger(ServiceProductionServiceImpl.class);

	@Autowired
	public ServiceProductionServiceImpl(ServiceProductionRepository serviceProductionRepository) {
		this.serviceProductionRepository = serviceProductionRepository;
	}

	@Override
	public List<ServiceProduction> findAll(ServiceProductionCriteria criteria) {

		if (criteria.isEmpty()) {
			return serviceProductionRepository.findAll();
		} else {
			Predicate predicate = buildPredicate(criteria);
			return ControllerUtils.iterableToArrayList(serviceProductionRepository.findAll(predicate));
		}
	}

	@Override
	public ServiceProduction find(Long id) {
		return serviceProductionRepository.findById(id).orElse(null);
	}

	Predicate buildPredicate(ServiceProductionCriteria criteria) {
		BooleanBuilder builder = new BooleanBuilder();
		if (criteria.getPhysicalAddress() != null) {
			builder.and(QServiceProduction.serviceProduction.physicalAddress.eq(criteria
					.getPhysicalAddress()));
		}
		if (criteria.getRivtaProfile() != null) {
			builder.and(QServiceProduction.serviceProduction.rivtaProfile.eq(criteria
					.getRivtaProfile()));
		}
		if (criteria.getServiceProducerId() != null) {
			builder.and(QServiceProduction.serviceProduction.serviceProducer.id.eq(criteria
					.getServiceProducerId()));
		}
		if (criteria.getLogicalAddressId() != null) {
			builder.and(QServiceProduction.serviceProduction.logicalAddress.id.eq(criteria
					.getLogicalAddressId()));
		}
		if (criteria.getServiceContractId() != null) {
			builder.and(QServiceProduction.serviceProduction.serviceContract.id.eq(criteria
					.getServiceContractId()));
		}
		if (criteria.getConnectionPointId() != null) {
			builder.and(QServiceProduction.serviceProduction.connectionPoint.id.eq(criteria
					.getConnectionPointId()));
		}
		if (criteria.getDomainId() != null) {
			builder.and(QServiceProduction.serviceProduction.serviceContract.serviceDomain.id.eq(
					criteria.getDomainId()));
		}
		return builder.getValue();
	}
}
