/**
 * Copyright (c) 2014 Center for eHalsa i samverkan (CeHis).
 * 								<http://cehis.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
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
