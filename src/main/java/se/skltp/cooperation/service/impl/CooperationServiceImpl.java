package se.skltp.cooperation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.skltp.cooperation.domain.Cooperation;
import se.skltp.cooperation.domain.QCooperation;
import se.skltp.cooperation.repository.CooperationRepository;
import se.skltp.cooperation.service.CooperationCriteria;
import se.skltp.cooperation.service.CooperationService;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

/**
 * @author Peter Merikan
 */
@Service
@Transactional(readOnly = true)
public class CooperationServiceImpl implements CooperationService {

	private final CooperationRepository cooperationRepository;

	@Autowired
	public CooperationServiceImpl(CooperationRepository cooperationRepository) {
		this.cooperationRepository = cooperationRepository;
	}

	@Override
	public List<Cooperation> findAll(CooperationCriteria criteria) {
		Predicate predicate = buildPredicate(criteria);

		if (criteria.isEmpty()) {
			return cooperationRepository.findAll();
		} else {
			return Lists.newArrayList(cooperationRepository.findAll(predicate));
		}
	}

	@Override
	public Cooperation find(Long id) {
		return cooperationRepository.findOne(id);
	}

	Predicate buildPredicate(CooperationCriteria criteria) {
		BooleanBuilder builder = new BooleanBuilder();
		if (criteria.getServiceConsumerId() != null) {
			builder.and(QCooperation.cooperation.serviceConsumer.id.eq(criteria.getServiceConsumerId()));
		}
		if (criteria.getLogicalAddressId() != null) {
			builder.and(QCooperation.cooperation.logicalAddress.id.eq(criteria.getLogicalAddressId()));
		}
		if (criteria.getServiceContractId() != null) {
			builder.and(QCooperation.cooperation.serviceContract.id.eq(criteria.getServiceContractId()));
		}
		if (criteria.getConnectionPointId() != null) {
			builder.and(QCooperation.cooperation.connectionPoint.id.eq(criteria.getConnectionPointId()));
		}
		return builder.getValue();
	}

}
