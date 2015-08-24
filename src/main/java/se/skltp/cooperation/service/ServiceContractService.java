package se.skltp.cooperation.service;

import se.skltp.cooperation.domain.ServiceContract;

import java.util.List;

/**
 * @author Jan Vasternas
 */
public interface ServiceContractService {

	/**
	 * Find all ServiceContracts
	 *
	 * @return List A list of {@link ServiceContract} objects.
	 */
	List<ServiceContract> findAll(ServiceContractCriteria criteria);

	/**
	 * Find a ServiceContract by id
	 *
	 * @param id
	 * @return ServiceContract
	 */
	ServiceContract find(Long id);
}
