package se.skltp.cooperation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import se.skltp.cooperation.domain.Cooperation;

/**
 * Spring Data JPA repository for the Cooperation entity.
 *
 * @author Peter Merikan
 */
public interface CooperationRepository extends JpaRepository<Cooperation,Long>, QueryDslPredicateExecutor {

}
