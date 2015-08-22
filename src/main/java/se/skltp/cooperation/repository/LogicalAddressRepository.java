package se.skltp.cooperation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.LogicalAddress;

/**
 * Spring Data JPA repository for the LogicalAddress entity.
 */
public interface LogicalAddressRepository extends JpaRepository<LogicalAddress, Long>, QueryDslPredicateExecutor<LogicalAddress>{

}
