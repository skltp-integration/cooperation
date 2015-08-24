package se.skltp.cooperation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import se.skltp.cooperation.domain.ServiceContract;

/**
 * Spring Data JPA repository for the ServiceContract entity.
 */
public interface ServiceContractRepository extends JpaRepository<ServiceContract, Long> ,QueryDslPredicateExecutor<ServiceContract>{

}
