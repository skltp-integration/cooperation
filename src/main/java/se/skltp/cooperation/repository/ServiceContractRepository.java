package se.skltp.cooperation.repository;

import se.skltp.cooperation.domain.ServiceContract;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ServiceContract entity.
 */
public interface ServiceContractRepository extends JpaRepository<ServiceContract,Long> {

}
