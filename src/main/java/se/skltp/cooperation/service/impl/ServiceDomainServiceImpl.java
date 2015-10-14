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

import se.skltp.cooperation.domain.QServiceDomain;
import se.skltp.cooperation.domain.ServiceDomain;
import se.skltp.cooperation.repository.ServiceDomainRepository;
import se.skltp.cooperation.service.ServiceDomainCriteria;
import se.skltp.cooperation.service.ServiceDomainService;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

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
			return Lists.newArrayList(serviceDomainRepository.findAll(predicate));
		}

	}

	@Override
	public ServiceDomain find(Long id) {
		return serviceDomainRepository.findOne(id);
	}

	Predicate buildPredicate(ServiceDomainCriteria criteria) {
		BooleanBuilder builder = new BooleanBuilder();
		if (criteria.getNamespace() != null) {
			builder.and(QServiceDomain.serviceDomain.namespace.startsWith(criteria.getNamespace()));
		}
		return builder.getValue();
	}

}
