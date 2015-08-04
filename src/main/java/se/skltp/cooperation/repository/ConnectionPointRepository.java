package se.skltp.cooperation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.skltp.cooperation.domain.ConnectionPoint;

/**
 * Spring Data JPA repository for the ConnectionPoint entity.
 */
public interface ConnectionPointRepository extends JpaRepository<ConnectionPoint, Long> {

}
