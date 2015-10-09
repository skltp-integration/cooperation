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
package se.skltp.cooperation.service.impl;

import com.mysema.query.types.Predicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import se.skltp.cooperation.Application;
import se.skltp.cooperation.domain.Cooperation;
import se.skltp.cooperation.repository.CooperationRepository;
import se.skltp.cooperation.service.CooperationCriteria;
import se.skltp.cooperation.service.CooperationCriteriaBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Peter Merikan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CooperationServiceImplTest {

	private CooperationServiceImpl uut;
	@Mock
	private CooperationRepository cooperationRepositoryMock;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		uut = new CooperationServiceImpl(cooperationRepositoryMock);
	}

	@Test
	public void findAll_shouldReturnAll() throws Exception {
		Cooperation c1 = new Cooperation();
		c1.setId(1L);
		Cooperation c2 = new Cooperation();
		c2.setId(2L);
		when(cooperationRepositoryMock.findAll()).thenReturn(Arrays.asList(c1, c2));
		List<Cooperation> result = uut.findAll(new CooperationCriteria());
		assertEquals(2, result.size());
		assertEquals(1, result.get(0).getId().longValue());
		assertEquals(2, result.get(1).getId().longValue());
		verify(cooperationRepositoryMock, times(1)).findAll();
	}

	@Test
	public void findAll_withPredicateShouldReturnAll() throws Exception {
		CooperationCriteria criteria = new CooperationCriteria();
		criteria.setConnectionPointId(1L);
		Cooperation c1 = new Cooperation();
		c1.setId(1L);
		Cooperation c2 = new Cooperation();
		c2.setId(2L);
		when(cooperationRepositoryMock.findAll(any(Predicate.class))).thenReturn(Arrays.asList(c1, c2));
		List<Cooperation> result = uut.findAll(criteria);
		assertEquals(2, result.size());
		assertEquals(1, result.get(0).getId().longValue());
		assertEquals(2, result.get(1).getId().longValue());
		verify(cooperationRepositoryMock, times(1)).findAll(any(Predicate.class));
	}

	@Test
	public void findAll_shouldReturnEmptyList() throws Exception {

		when(cooperationRepositoryMock.findAll()).thenReturn(new ArrayList<Cooperation>());
		List<Cooperation> result = uut.findAll(new CooperationCriteria());
		assertEquals(0, result.size());
	}

	@Test
	public void find_shouldReturnOne() throws Exception {
		Cooperation c1 = new Cooperation();
		c1.setId(1L);
		when(cooperationRepositoryMock.findOne(c1.getId())).thenReturn(c1);
		Cooperation result = uut.find(c1.getId());
		assertEquals(1, result.getId().longValue());
	}

	@Test
	public void find_shouldReturnNullWhenNotFound() throws Exception {
		Cooperation c1 = new Cooperation();
		c1.setId(1L);
		when(cooperationRepositoryMock.findOne(c1.getId())).thenReturn(null);
		Cooperation result = uut.find(c1.getId());
		assertNull(result);
		verify(cooperationRepositoryMock, times(1)).findOne(c1.getId());

	}

	@Test
	public void buildPredicate_shouldBuild() throws Exception {


		Predicate predicate = uut.buildPredicate(new CooperationCriteriaBuilder()
			.serviceConsumerId(1L).build());
		assertThat(predicate.toString(), is("cooperation.serviceConsumer.id = 1"));
		predicate = uut.buildPredicate(new CooperationCriteriaBuilder()
			.serviceConsumerId(1L)
			.logicalAddressId(2L).build());
		assertThat(predicate.toString(), is("cooperation.serviceConsumer.id = 1 && cooperation.logicalAddress.id = 2"));
		predicate = uut.buildPredicate(new CooperationCriteriaBuilder()
			.serviceConsumerId(1L)
			.logicalAddressId(2L)
			.serviceContractId(3L).build());
		predicate = uut.buildPredicate(new CooperationCriteriaBuilder()
			.serviceConsumerId(1L)
			.logicalAddressId(2L)
			.serviceContractId(3L)
			.connectionPointId(4L).build());
		assertThat(predicate.toString(), is("cooperation.serviceConsumer.id = 1 && cooperation.logicalAddress.id = 2 && cooperation.serviceContract.id = 3 && cooperation.connectionPoint.id = 4"));

	}

	@Test
	public void buildCriteria_shouldReturnNull() throws Exception {

		Predicate predicate = uut.buildPredicate(new CooperationCriteria());
		assertNull(predicate);
	}


}
