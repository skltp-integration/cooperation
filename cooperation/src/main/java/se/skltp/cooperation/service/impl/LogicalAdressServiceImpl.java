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

import se.skltp.cooperation.domain.LogicalAddress;
import se.skltp.cooperation.domain.QLogicalAddress;
import se.skltp.cooperation.repository.LogicalAddressRepository;
import se.skltp.cooperation.service.LogicalAddressCriteria;

import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

/**
 * @author Jan Vasternas
 */
@Service
@Transactional(readOnly = true)
public class LogicalAdressServiceImpl implements se.skltp.cooperation.service.LogicalAddressService {

	private final LogicalAddressRepository logicalAddressRepository;

	@Autowired
	public LogicalAdressServiceImpl(LogicalAddressRepository LogicalAddressRepository) {
		this.logicalAddressRepository = LogicalAddressRepository;
	}

	@Override
	public List<LogicalAddress> findAll(LogicalAddressCriteria criteria) {
		if (criteria.isEmpty()) {
			return logicalAddressRepository.findAll();
		} else {
			Predicate predicate = buildPredicate(criteria);
			return Lists.newArrayList(logicalAddressRepository.findAll(predicate));
		}
	}

	@Override
	public LogicalAddress find(Long id) {
		return logicalAddressRepository.findById(id).orElse(null);
	}

	Predicate buildPredicate(LogicalAddressCriteria criteria) {
		BooleanBuilder builder = new BooleanBuilder();
		if (criteria.getLogicalAdress() != null) {
			builder.and(QLogicalAddress.logicalAddress1.logicalAddress.eq(criteria
					.getLogicalAdress()));
		}
		if (criteria.getServiceConsumerId() != null) {
			builder.and(QLogicalAddress.logicalAddress1.cooperations.any().serviceConsumer.id
					.eq(criteria.getServiceConsumerId()));
		}
		if (criteria.getServiceContractId() != null) {
			builder.and(QLogicalAddress.logicalAddress1.serviceProductions.any().serviceContract.id
					.eq(criteria.getServiceContractId()));
		}
		if (criteria.getConnectionPointId() != null) {
			builder.and(QLogicalAddress.logicalAddress1.serviceProductions.any().connectionPoint.id
					.eq(criteria.getConnectionPointId()));
		}
		if (criteria.getServiceProducerId() != null) {
			builder.and(QLogicalAddress.logicalAddress1.serviceProductions.any().serviceProducer.id
					.eq(criteria.getServiceProducerId()));
		}
		return builder.getValue();
	}

}
