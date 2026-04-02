/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.skltp.cooperation.domain.QServiceDomain;
import se.skltp.cooperation.domain.ServiceDomain;
import se.skltp.cooperation.repository.ServiceDomainRepository;
import se.skltp.cooperation.service.ServiceDomainCriteria;
import se.skltp.cooperation.service.ServiceDomainService;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import se.skltp.cooperation.util.ControllerUtils;

/**
 * @author Jan Vasternas
 */
@Service
@Transactional(readOnly = true)
public class ServiceDomainServiceImpl implements ServiceDomainService {

	private final ServiceDomainRepository serviceDomainRepository;

	@Autowired
	public ServiceDomainServiceImpl(ServiceDomainRepository serviceDomainRepository) {
		this.serviceDomainRepository = serviceDomainRepository;
	}

	@Override
	public List<ServiceDomain> findAll(ServiceDomainCriteria criteria) {

		if (criteria.isEmpty()) {
			return serviceDomainRepository.findAll();
		} else {
			Predicate predicate = buildPredicate(criteria);
			return ControllerUtils.iterableToArrayList(serviceDomainRepository.findAll(predicate));
		}

	}

	@Override
	public ServiceDomain find(Long id) {
		return serviceDomainRepository.findById(id).orElse(null);
	}

	Predicate buildPredicate(ServiceDomainCriteria criteria) {
		BooleanBuilder builder = new BooleanBuilder();
		if (criteria.getNamespace() != null) {
			builder.and(QServiceDomain.serviceDomain.namespace.startsWith(criteria.getNamespace()));
		}
		return builder.getValue();
	}

}
