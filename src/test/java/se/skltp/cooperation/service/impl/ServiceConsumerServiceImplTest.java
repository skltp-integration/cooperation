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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import se.skltp.cooperation.Application;
import se.skltp.cooperation.domain.ServiceConsumer;
import se.skltp.cooperation.repository.ServiceConsumerRepository;
import se.skltp.cooperation.service.ServiceConsumerCriteria;

import com.querydsl.core.types.Predicate;

/**
 * Tests for {@link ServiceConsumerServiceImpl}
 *
 * @author Peter Merikan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ServiceConsumerServiceImplTest {

	private ServiceConsumerServiceImpl uut;
	@Mock
	private ServiceConsumerRepository serviceConsumerRepositoryMock;
	private ServiceConsumer sc1;
	private ServiceConsumer sc2;


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		uut = new ServiceConsumerServiceImpl(serviceConsumerRepositoryMock);
	}

	@Before
	public void setUpTestData() throws Exception {
		sc1 = new ServiceConsumer();
		sc1.setId(1L);
		sc2 = new ServiceConsumer();
		sc2.setId(2L);

	}

	@Test
	public void findAll_shouldReturnAll() throws Exception {
		when(serviceConsumerRepositoryMock.findAll()).thenReturn(Arrays.asList(sc1, sc2));
		List<ServiceConsumer> result = uut.findAll();
		assertEquals(2, result.size());
		assertEquals(1, result.get(0).getId().longValue());
		assertEquals(2, result.get(1).getId().longValue());
		verify(serviceConsumerRepositoryMock, times(1)).findAll();
	}

	@Test
	public void findAll_withCriteriaShouldReturnAll() throws Exception {
		ServiceConsumerCriteria criteria = new ServiceConsumerCriteria();
		criteria.setConnectionPointId(sc1.getId());
		when(serviceConsumerRepositoryMock.findAll(any(Predicate.class))).thenReturn(Arrays.asList(sc1, sc2));
		List<ServiceConsumer> result = uut.findAll(criteria);
		assertEquals(2, result.size());
		assertEquals(1, result.get(0).getId().longValue());
		assertEquals(2, result.get(1).getId().longValue());
		verify(serviceConsumerRepositoryMock, times(1)).findAll(any(Predicate.class));
	}

	@Test
	public void findAll_shouldReturnEmptyList() throws Exception {

		when(serviceConsumerRepositoryMock.findAll()).thenReturn(new ArrayList<ServiceConsumer>());
		List<ServiceConsumer> result = uut.findAll();
		assertEquals(0, result.size());
	}

	@Test
	public void find_shouldReturnOne() throws Exception {
		when(serviceConsumerRepositoryMock.findById(sc1.getId())).thenReturn(Optional.of(sc1));
		ServiceConsumer result = uut.find(sc1.getId());
		assertEquals(1, result.getId().longValue());
	}

	@Test
	public void find_shouldReturnNullWhenNotFound() throws Exception {
		Optional<ServiceConsumer> osc1 = Optional.empty();
		when(serviceConsumerRepositoryMock.findById(sc1.getId())).thenReturn(osc1);
		ServiceConsumer result = uut.find(sc1.getId());
		assertNull(result);
		verify(serviceConsumerRepositoryMock, times(1)).findById(sc1.getId());

	}


}
