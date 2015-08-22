package se.skltp.cooperation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.ServiceProducer;

/**
 * Spring Data JPA repository for the ServiceProducer entity.
 */
public interface ServiceProducerRepository extends JpaRepository<ServiceProducer, Long> ,QueryDslPredicateExecutor<ServiceProducer>{

}
