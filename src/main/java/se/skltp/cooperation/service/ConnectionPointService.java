package se.skltp.cooperation.service;

import se.skltp.cooperation.domain.ConnectionPoint;

import java.util.List;

/**
 * @author Peter Merikan
 */
public interface ConnectionPointService {

	/**
	 * Find all ConnectionPoints
	 *
	 * @return List A list of {@link ConnectionPoint} objects.
	 */
	List<ConnectionPoint> findAll();

	/**
	 * Find a ConnectionPoint by id
	 *
	 * @param id
	 * @return ConnectionPoint
	 */
	ConnectionPoint find(Long id);
}
