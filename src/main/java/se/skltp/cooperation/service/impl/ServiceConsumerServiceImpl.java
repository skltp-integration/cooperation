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
