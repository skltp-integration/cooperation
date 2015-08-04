package se.skltp.cooperation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.skltp.cooperation.domain.ServiceProduction;

/**
 * Spring Data JPA repository for the ServiceProduction entity.
 */
public interface ServiceProductionRepository extends JpaRepository<ServiceProduction, Long> {

}
