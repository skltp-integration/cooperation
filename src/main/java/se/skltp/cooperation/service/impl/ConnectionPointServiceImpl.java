package se.skltp.cooperation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.repository.ConnectionPointRepository;

import java.util.List;

/**
 * @author Peter Merikan
 */
@Service
@Transactional(readOnly = true)
public class ConnectionPointServiceImpl implements se.skltp.cooperation.service.ConnectionPointService {

	private final ConnectionPointRepository connectionPointRepository;

	@Autowired
	public ConnectionPointServiceImpl(ConnectionPointRepository connectionPointRepository) {
		this.connectionPointRepository = connectionPointRepository;
	}

	@Override
	public List<ConnectionPoint> findAll() {
		return connectionPointRepository.findAll();
	}

	@Override
	public ConnectionPoint find(Long id) {
		return connectionPointRepository.findOne(id);
	}

}
