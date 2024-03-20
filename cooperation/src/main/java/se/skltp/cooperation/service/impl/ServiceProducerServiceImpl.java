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

import se.skltp.cooperation.domain.QLogicalAddress;
import se.skltp.cooperation.domain.QServiceProducer;
import se.skltp.cooperation.domain.ServiceProducer;
import se.skltp.cooperation.repository.ServiceProducerRepository;
import se.skltp.cooperation.service.ServiceProducerCriteria;

import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;

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
			return Lists.newArrayList(serviceProducerRepository.findAll(predicate));
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
