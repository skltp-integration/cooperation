package se.skltp.cooperation.service;

import java.util.List;

import se.skltp.cooperation.domain.ServiceProduction;

/**
 * @author Peter Merikan
 */
public interface ServiceProductionService {

	/**
	 * Find all ServiceProductions by given criteria
	 *
	 * @param criteria
	 * @return List A list of {@link ServiceProduction} objects.
	 */
	List<ServiceProduction> findAll(ServiceProductionCriteria criteria);

	/**
	 * Find a cooperation by id
	 *
	 * @param id
	 * @return ServiceProduction
	 */
	ServiceProduction find(Long id);
}
