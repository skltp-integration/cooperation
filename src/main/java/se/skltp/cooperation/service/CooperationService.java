package se.skltp.cooperation.service;

import se.skltp.cooperation.domain.Cooperation;

import java.util.List;

/**
 * @author Peter Merikan
 */
public interface CooperationService {

	/**
	 * Find all Cooperations
	 *
	 * @return List A list of {@link Cooperation} objects.
	 */
	List<Cooperation> findAll();

	/**
	 * Find all Cooperations by given criteria
	 *
	 * @param criteria
	 * @return List A list of {@link Cooperation} objects.
	 */
	List<Cooperation> findAll(CooperationCriteria criteria);

	/**
	 * Find a cooperation by id
	 *
	 * @param id
	 * @return Cooperation
	 */
	Cooperation find(Long id);
}
