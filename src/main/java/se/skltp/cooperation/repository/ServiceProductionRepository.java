package se.skltp.cooperation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.ServiceProduction;

/**
 * Spring Data JPA repository for the ServiceProduction entity.
 */
public interface ServiceProductionRepository extends JpaRepository<ServiceProduction, Long>,QueryDslPredicateExecutor<ServiceProduction> {

}
