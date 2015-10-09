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

import se.skltp.cooperation.domain.QServiceConsumer;
import se.skltp.cooperation.domain.ServiceConsumer;
import se.skltp.cooperation.repository.ServiceConsumerRepository;
import se.skltp.cooperation.service.ServiceConsumerCriteria;
import se.skltp.cooperation.service.ServiceConsumerService;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

/**
 * @author Peter Merikan
 */
@Service
@Transactional(readOnly = true)
public class ServiceConsumerServiceImpl implements ServiceConsumerService {

	private final ServiceConsumerRepository serviceConsumerRepository;

	@Autowired
	public ServiceConsumerServiceImpl(ServiceConsumerRepository serviceConsumerRepository) {
		this.serviceConsumerRepository = serviceConsumerRepository;
	}

	@Override
	public List<ServiceConsumer> findAll() {
		return serviceConsumerRepository.findAll();
	}

	@Override
	public List<ServiceConsumer> findAll(ServiceConsumerCriteria criteria) {
		if (criteria.isEmpty()) {
			return findAll();
		} else {
			Predicate predicate = buildPredicate(criteria);
			return Lists.newArrayList(serviceConsumerRepository.findAll(predicate));
		}
	}

	@Override
	public ServiceConsumer find(Long id) {
		return serviceConsumerRepository.findOne(id);
	}

	Predicate buildPredicate(ServiceConsumerCriteria criteria) {
		BooleanBuilder builder = new BooleanBuilder();
		if (criteria.getConnectionPointId() != null) {
			builder.and(QServiceConsumer.serviceConsumer.cooperations.any().connectionPoint.id
					.eq(criteria.getConnectionPointId()));
		}
		if (criteria.getLogicalAddressId() != null) {
			builder.and(QServiceConsumer.serviceConsumer.cooperations.any().logicalAddress.id
					.eq(criteria.getLogicalAddressId()));
		}
		if (criteria.getServiceContractId() != null) {
			builder.and(QServiceConsumer.serviceConsumer.cooperations.any().serviceContract.id
					.eq(criteria.getServiceContractId()));
		}
		return builder.getValue();
	}

}
