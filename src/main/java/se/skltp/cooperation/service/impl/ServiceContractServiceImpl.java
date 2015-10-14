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

import se.skltp.cooperation.domain.QServiceContract;
import se.skltp.cooperation.domain.ServiceContract;
import se.skltp.cooperation.repository.ServiceContractRepository;
import se.skltp.cooperation.service.ServiceContractCriteria;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

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
			return Lists.newArrayList(serviceContractRepository.findAll(predicate));
		}
	}

	@Override
	public ServiceContract find(Long id) {
		return serviceContractRepository.findOne(id);
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
