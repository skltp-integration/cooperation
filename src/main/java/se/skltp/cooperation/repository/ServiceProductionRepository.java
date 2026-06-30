/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import se.skltp.cooperation.domain.ServiceProduction;

import java.util.List;

/**
 * Spring Data JPA repository for the ServiceProduction entity.
 * An EntityGraph is used to improve performance by reducing database calls for typical API usage.
 */
public interface ServiceProductionRepository extends JpaRepository<ServiceProduction, Long>,QuerydslPredicateExecutor<ServiceProduction> {
	@EntityGraph(value = "ServiceProduction.eager", type = EntityGraph.EntityGraphType.FETCH)
	List<ServiceProduction> findAll();

	@EntityGraph(value = "ServiceProduction.eager", type = EntityGraph.EntityGraphType.FETCH)
	Iterable<ServiceProduction> findAll(Predicate predicate);
}
