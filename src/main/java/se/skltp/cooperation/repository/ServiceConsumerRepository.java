/**
 * Copyright (c) 2015-2026 Inera.
 * * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import se.skltp.cooperation.domain.ServiceConsumer;

/**
 * Spring Data JPA repository for the ServiceConsumer entity.
 */
public interface ServiceConsumerRepository extends JpaRepository<ServiceConsumer, Long>,
		QuerydslPredicateExecutor<ServiceConsumer> {

}
