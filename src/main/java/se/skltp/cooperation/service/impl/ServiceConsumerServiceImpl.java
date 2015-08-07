package se.skltp.cooperation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.skltp.cooperation.domain.ServiceConsumer;
import se.skltp.cooperation.repository.ServiceConsumerRepository;
import se.skltp.cooperation.service.ServiceConsumerCriteria;
import se.skltp.cooperation.service.ServiceConsumerService;

import java.util.List;

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
			return serviceConsumerRepository.findDistinctByCooperationsConnectionPointIdOrderByDescriptionAsc(criteria.getConnectionPointId());
		}
	}

	@Override
	public ServiceConsumer find(Long id) {
		return serviceConsumerRepository.findOne(id);
	}

}
