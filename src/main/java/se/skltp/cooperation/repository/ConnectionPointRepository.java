package se.skltp.cooperation.repository;

import se.skltp.cooperation.domain.ConnectionPoint;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ConnectionPoint entity.
 */
public interface ConnectionPointRepository extends JpaRepository<ConnectionPoint,Long> {

}
