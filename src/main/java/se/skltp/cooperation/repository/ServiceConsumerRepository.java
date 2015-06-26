package se.skltp.cooperation.repository;

import se.skltp.cooperation.domain.ServiceConsumer;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ServiceConsumer entity.
 */
public interface ServiceConsumerRepository extends JpaRepository<ServiceConsumer,Long> {

}
