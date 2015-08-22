package se.skltp.cooperation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import se.skltp.cooperation.domain.ServiceConsumer;

/**
 * Spring Data JPA repository for the ServiceConsumer entity.
 */
public interface ServiceConsumerRepository extends JpaRepository<ServiceConsumer, Long>,
		QueryDslPredicateExecutor<ServiceConsumer> {

}
