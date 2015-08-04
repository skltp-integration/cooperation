package se.skltp.cooperation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.skltp.cooperation.domain.LogicalAddress;

/**
 * Spring Data JPA repository for the LogicalAddress entity.
 */
public interface LogicalAddressRepository extends JpaRepository<LogicalAddress, Long> {

}
