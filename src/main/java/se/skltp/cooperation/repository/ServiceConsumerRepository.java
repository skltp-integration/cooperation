package se.skltp.cooperation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.skltp.cooperation.domain.ServiceConsumer;

import java.util.List;

/**
 * Spring Data JPA repository for the ServiceConsumer entity.
 */
public interface ServiceConsumerRepository extends JpaRepository<ServiceConsumer, Long> {

	List<ServiceConsumer> findDistinctByCooperationsConnectionPointIdOrderByDescriptionAsc(Long cooperationId);

}
