package se.skltp.cooperation.repository;

import se.skltp.cooperation.domain.LogicalAddress;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LogicalAddress entity.
 */
public interface LogicalAddressRepository extends JpaRepository<LogicalAddress,Long> {

}
