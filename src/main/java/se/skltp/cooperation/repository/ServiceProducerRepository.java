package se.skltp.cooperation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.skltp.cooperation.domain.ServiceProducer;

/**
 * Spring Data JPA repository for the ServiceProducer entity.
 */
public interface ServiceProducerRepository extends JpaRepository<ServiceProducer, Long> {

}
