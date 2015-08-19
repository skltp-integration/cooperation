package se.skltp.cooperation.service;

import java.util.List;

import se.skltp.cooperation.domain.ServiceProducer;

/**
 * @author Jan Vasternas
 */
public interface ServiceProducerService {

	/**
	 * Find all ServiceProducers
	 *
	 * @return List A list of {@link ServiceProducer} objects.
	 */
	List<ServiceProducer> findAll();

	/**
	 * Find a ServiceProducer by id
	 *
	 * @param id
	 * @return ConnectionPoint
	 */
	ServiceProducer find(Long id);
}
