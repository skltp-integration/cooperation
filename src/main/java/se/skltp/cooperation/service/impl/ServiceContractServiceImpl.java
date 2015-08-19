package se.skltp.cooperation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.skltp.cooperation.domain.ServiceContract;
import se.skltp.cooperation.repository.ServiceContractRepository;

/**
 * @author Jan Vasternas
 */
@Service
@Transactional(readOnly = true)
public class ServiceContractServiceImpl implements se.skltp.cooperation.service.ServiceContractService {

	private final ServiceContractRepository ServiceContractRepository;

	@Autowired
	public ServiceContractServiceImpl(ServiceContractRepository ServiceContractRepository) {
		this.ServiceContractRepository = ServiceContractRepository;
	}

	@Override
	public List<ServiceContract> findAll() {
		return ServiceContractRepository.findAll();
	}

	@Override
	public ServiceContract find(Long id) {
		return ServiceContractRepository.findOne(id);
	}

}
