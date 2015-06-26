package se.skltp.cooperation.repository;

import se.skltp.cooperation.domain.ServiceProduction;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ServiceProduction entity.
 */
public interface ServiceProductionRepository extends JpaRepository<ServiceProduction,Long> {

}
