package se.skltp.cooperation.service;

import se.skltp.cooperation.domain.LogicalAddress;

import java.util.List;

/**
 * @author Jan Vasternas
 */
public interface LogicalAddressService {

	/**
	 * Find all LogicalAddresss
	 *
	 * @return List A list of {@link LogicalAddress} objects.
	 */
	List<LogicalAddress> findAll(LogicalAddressCriteria criteria);

	/**
	 * Find a LogicalAddress by id
	 *
	 * @param id
	 * @return LogicalAddress
	 */
	LogicalAddress find(Long id);
}
