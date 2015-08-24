package se.skltp.cooperation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.skltp.cooperation.domain.QServiceProducer;
import se.skltp.cooperation.domain.ServiceProducer;
import se.skltp.cooperation.repository.ServiceProducerRepository;
import se.skltp.cooperation.service.ServiceProducerCriteria;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

/**
 * @author Jan Vasternas
 */
@Service
@Transactional(readOnly = true)
public class ServiceProducerServiceImpl implements se.skltp.cooperation.service.ServiceProducerService {

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
		return serviceProducerRepository.findOne(id);
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
			builder.and(QServiceProducer.serviceProducer.serviceProductions.any().connectionPoint.id
					.eq(criteria.getConnectionPointId()));
		}
		return builder.getValue();
	}

}
