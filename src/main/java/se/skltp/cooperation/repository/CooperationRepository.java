/**
 * Copyright (c) 2015-2026 Inera.
 * * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import se.skltp.cooperation.domain.Cooperation;

import java.util.List;

/**
 * Spring Data JPA repository for the Cooperation entity.
 * An EntityGraph is used to improve performance by reducing database calls for typical API usage.
 */
public interface CooperationRepository extends JpaRepository<Cooperation, Long>, QuerydslPredicateExecutor<Cooperation> {
	@EntityGraph(value = "Cooperation.eager", type = EntityGraph.EntityGraphType.FETCH)
	List<Cooperation> findAll();

	@EntityGraph(value = "Cooperation.eager", type = EntityGraph.EntityGraphType.FETCH)
	Iterable<Cooperation> findAll(Predicate predicate);
}
