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
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

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
		return logicalAddressRepository.findOne(id);
	}

	Predicate buildPredicate(LogicalAddressCriteria criteria) {
		BooleanBuilder builder = new BooleanBuilder();
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
