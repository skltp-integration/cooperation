package se.skltp.cooperation.service.impl;

import com.google.common.collect.Lists;
import com.mysema.query.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.skltp.cooperation.domain.Cooperation;
import se.skltp.cooperation.repository.CooperationRepository;
import se.skltp.cooperation.service.CooperationService;

import java.util.List;

/**
 * @author Peter Merikan
 */
@Service
@Transactional(readOnly = true)
public class CooperationServiceImpl implements CooperationService{

	private final CooperationRepository cooperationRepository;

	@Autowired
	public CooperationServiceImpl(CooperationRepository cooperationRepository) {
		this.cooperationRepository = cooperationRepository;
	}

	@Override
	public List<Cooperation> findAll() {
		return cooperationRepository.findAll();
	}

	@Override
	public List<Cooperation> findAll(Predicate predicate) {
		return Lists.newArrayList(cooperationRepository.findAll(predicate));
	}

	@Override
	public Cooperation find(Long id) {
		return cooperationRepository.findOne(id);
	}
}
