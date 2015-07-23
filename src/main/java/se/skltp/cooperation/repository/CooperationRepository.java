package se.skltp.cooperation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.skltp.cooperation.domain.Cooperation;

/**
 * Spring Data JPA repository for the Cooperation entity.
 */
public interface CooperationRepository extends JpaRepository<Cooperation,Long> {

}
