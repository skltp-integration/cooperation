/**
 * Copyright (c) 2015-2026 Inera.
 * * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.service;

import se.skltp.cooperation.domain.ServiceConsumer;

import java.util.List;

/**
 */
public interface ServiceConsumerService {
	List<ServiceConsumer> findAll();

	List<ServiceConsumer> findAll(ServiceConsumerCriteria criteria);

	ServiceConsumer find(Long id);
}
