package se.skltp.cooperation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.QConnectionPoint;
import se.skltp.cooperation.repository.ConnectionPointRepository;
import se.skltp.cooperation.service.ConnectionPointCriteria;
import se.skltp.cooperation.service.ConnectionPointService;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

/**
 * @author Peter Merikan
 */
@Service
@Transactional(readOnly = true)
public class ConnectionPointServiceImpl implements ConnectionPointService {

	private final ConnectionPointRepository connectionPointRepository;

	@Autowired
	public ConnectionPointServiceImpl(ConnectionPointRepository connectionPointRepository) {
		this.connectionPointRepository = connectionPointRepository;
	}

	@Override
	public List<ConnectionPoint> findAll(ConnectionPointCriteria criteria) {

		if (criteria.isEmpty()) {
			return connectionPointRepository.findAll();
		} else {
			Predicate predicate = buildPredicate(criteria);
			return Lists.newArrayList(connectionPointRepository.findAll(predicate));
		}

	}

	@Override
	public ConnectionPoint find(Long id) {
		return connectionPointRepository.findOne(id);
	}

	Predicate buildPredicate(ConnectionPointCriteria criteria) {
		BooleanBuilder builder = new BooleanBuilder();
		if (criteria.getPlatform() != null) {
			builder.and(QConnectionPoint.connectionPoint.platform.eq(criteria.getPlatform()));
		}
		if (criteria.getEnvironment() != null) {
			builder.and(QConnectionPoint.connectionPoint.environment.eq(criteria.getEnvironment()));
		}
		if (criteria.getServiceConsumerId() != null) {
			builder.and(QConnectionPoint.connectionPoint.cooperations.any().serviceConsumer.id
					.eq(criteria.getServiceConsumerId()));
		}
		if (criteria.getLogicalAddressId() != null) {
			builder.and(QConnectionPoint.connectionPoint.serviceProductions.any().logicalAddress.id
					.eq(criteria.getLogicalAddressId()));
		}
		if (criteria.getServiceContractId() != null) {
			builder.and(QConnectionPoint.connectionPoint.serviceProductions.any().serviceContract.id
					.eq(criteria.getServiceContractId()));
		}
		if (criteria.getServiceProducerId() != null) {
			builder.and(QConnectionPoint.connectionPoint.serviceProductions.any().serviceProducer.id
					.eq(criteria.getServiceProducerId()));
		}
		return builder.getValue();
	}

}
