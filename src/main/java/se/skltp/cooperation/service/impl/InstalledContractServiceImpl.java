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

import se.skltp.cooperation.domain.InstalledContract;
import se.skltp.cooperation.domain.QInstalledContract;
import se.skltp.cooperation.repository.InstalledContractRepository;
import se.skltp.cooperation.service.InstalledContractCriteria;
import se.skltp.cooperation.service.InstalledContractService;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

/**
 * @author Jan Vasternas
 */
@Service
@Transactional(readOnly = true)
public class InstalledContractServiceImpl implements InstalledContractService {

	private final InstalledContractRepository installedContractRepository;

	@Autowired
	public InstalledContractServiceImpl(InstalledContractRepository installedContractRepository) {
		this.installedContractRepository = installedContractRepository;
	}

	@Override
	public List<InstalledContract> findAll(InstalledContractCriteria criteria) {
		if (criteria.isEmpty()) {
			return installedContractRepository.findAll();
		} else {
			Predicate predicate = buildPredicate(criteria);
			return Lists.newArrayList(installedContractRepository.findAll(predicate));
		}
	}

	@Override
	public InstalledContract find(Long id) {
		return installedContractRepository.findOne(id);
	}

	Predicate buildPredicate(InstalledContractCriteria criteria) {
		BooleanBuilder builder = new BooleanBuilder();
		if (criteria.getConnectionPointId() != null) {
			builder.and(QInstalledContract.installedContract.connectionPoint.id
					.eq(criteria.getConnectionPointId()));
		}
		if (criteria.getServiceContractId() != null) {
			builder.and(QInstalledContract.installedContract.serviceContract.id
					.eq(criteria.getServiceContractId()));
		}
		if (criteria.getServiceDomainId() != null) {
			builder.and(QInstalledContract.installedContract.serviceContract.serviceDomain.id
					.eq(criteria.getServiceDomainId()));
		}
		return builder.getValue();
	}

}
