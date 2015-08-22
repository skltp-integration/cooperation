package se.skltp.cooperation.service;

import java.util.List;

import se.skltp.cooperation.domain.ConnectionPoint;

/**
 * @author Peter Merikan
 */
public interface ConnectionPointService {

	/**
	 * Find all ConnectionPoints
	 * 
	 * @param criteria
	 *
	 * @return List A list of {@link ConnectionPoint} objects.
	 */
	List<ConnectionPoint> findAll(ConnectionPointCriteria criteria);

	/**
	 * Find a ConnectionPoint by id
	 *
	 * @param id
	 * @return ConnectionPoint
	 */
	ConnectionPoint find(Long id);
}
