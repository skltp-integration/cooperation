package se.skltp.cooperation.service;

import se.skltp.cooperation.domain.ServiceConsumer;

import java.util.List;

/**
 * @author Peter Merikan
 */
public interface ServiceConsumerService {
	List<ServiceConsumer> findAll();

	List<ServiceConsumer> findAll(ServiceConsumerCriteria criteria);

	ServiceConsumer find(Long id);
}
