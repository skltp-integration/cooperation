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

import se.skltp.cooperation.domain.InstalledContract;
import se.skltp.cooperation.domain.QInstalledContract;
import se.skltp.cooperation.repository.InstalledContractRepository;
import se.skltp.cooperation.service.InstalledContractCriteria;
import se.skltp.cooperation.service.InstalledContractService;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import se.skltp.cooperation.util.ControllerUtils;

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
			return ControllerUtils.iterableToArrayList(installedContractRepository.findAll(predicate));
		}
	}

	@Override
	public InstalledContract find(Long id) {
		return installedContractRepository.findById(id).orElse(null);
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
