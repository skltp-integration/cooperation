package se.skltp.cooperation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.skltp.cooperation.domain.ServiceProducer;
import se.skltp.cooperation.repository.ServiceProducerRepository;

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
	public List<ServiceProducer> findAll() {
		return serviceProducerRepository.findAll();
	}

	@Override
	public ServiceProducer find(Long id) {
		return serviceProducerRepository.findOne(id);
	}

}
