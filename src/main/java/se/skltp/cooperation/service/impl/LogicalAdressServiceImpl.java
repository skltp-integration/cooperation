/**
 * Copyright (c) 2015-2026 Inera.
 * * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
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

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import se.skltp.cooperation.util.ControllerUtils;

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
			return ControllerUtils.iterableToArrayList(logicalAddressRepository.findAll(predicate));
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
