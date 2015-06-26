package se.skltp.cooperation.repository;

import se.skltp.cooperation.domain.Cooperation;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cooperation entity.
 */
public interface CooperationRepository extends JpaRepository<Cooperation,Long> {

}
