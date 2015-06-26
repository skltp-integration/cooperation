package se.skltp.cooperation.repository;

import se.skltp.cooperation.domain.ServiceProducer;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ServiceProducer entity.
 */
public interface ServiceProducerRepository extends JpaRepository<ServiceProducer,Long> {

}
