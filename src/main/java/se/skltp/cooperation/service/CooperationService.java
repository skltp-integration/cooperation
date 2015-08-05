package se.skltp.cooperation.service;

import com.mysema.query.types.Predicate;
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
	 * Find all Cooperations by given predicate
	 *
	 * @param predicate
	 * @return List A list of {@link Cooperation} objects.
	 */
	List<Cooperation> findAll(Predicate predicate);

	/**
	 * Find a cooperation by id
	 *
	 * @param id
	 * @return Cooperation
	 */
	Cooperation find(Long id);
}
