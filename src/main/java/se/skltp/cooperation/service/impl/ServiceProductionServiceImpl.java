package se.skltp.cooperation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.skltp.cooperation.domain.QServiceProduction;
import se.skltp.cooperation.domain.ServiceProduction;
import se.skltp.cooperation.repository.ServiceProductionRepository;
import se.skltp.cooperation.service.ServiceProductionCriteria;
import se.skltp.cooperation.service.ServiceProductionService;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

/**
 * @author Jan Vasternas
 */
@Service
@Transactional(readOnly = true)
public class ServiceProductionServiceImpl implements ServiceProductionService {

	private final ServiceProductionRepository serviceProductionRepository;

	@Autowired
	public ServiceProductionServiceImpl(ServiceProductionRepository serviceProductionRepository) {
		this.serviceProductionRepository = serviceProductionRepository;
	}

	@Override
	public List<ServiceProduction> findAll(ServiceProductionCriteria criteria) {
		Predicate predicate = buildPredicate(criteria);

		if (criteria.isEmpty()) {
			return serviceProductionRepository.findAll();
		} else {
			return Lists.newArrayList(serviceProductionRepository.findAll(predicate));
		}
	}

	@Override
	public ServiceProduction find(Long id) {
		return serviceProductionRepository.findOne(id);
	}

	Predicate buildPredicate(ServiceProductionCriteria criteria) {
		BooleanBuilder builder = new BooleanBuilder();
		if (criteria.getPhysicalAddress() != null) {
			builder.and(QServiceProduction.serviceProduction.physicalAddress.eq(criteria.getPhysicalAddress()));
		}
		if (criteria.getRivtaProfile() != null) {
			builder.and(QServiceProduction.serviceProduction.rivtaProfile.eq(criteria.getRivtaProfile()));
		}
		if (criteria.getServiceProducerId() != null) {
			builder.and(QServiceProduction.serviceProduction.serviceProducer.id.eq(criteria.getServiceProducerId()));
		}
		if (criteria.getLogicalAddressId() != null) {
			builder.and(QServiceProduction.serviceProduction.logicalAddress.id.eq(criteria.getLogicalAddressId()));
		}
		if (criteria.getServiceContractId() != null) {
			builder.and(QServiceProduction.serviceProduction.serviceContract.id.eq(criteria.getServiceContractId()));
		}
		if (criteria.getConnectionPointId() != null) {
			builder.and(QServiceProduction.serviceProduction.connectionPoint.id.eq(criteria.getConnectionPointId()));
		}
		return builder.getValue();
	}

}
