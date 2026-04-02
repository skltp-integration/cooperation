/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import se.skltp.cooperation.domain.ServiceContract;

/**
 * Spring Data JPA repository for the ServiceContract entity.
 */
public interface ServiceContractRepository extends JpaRepository<ServiceContract, Long> ,QuerydslPredicateExecutor<ServiceContract>{

}
