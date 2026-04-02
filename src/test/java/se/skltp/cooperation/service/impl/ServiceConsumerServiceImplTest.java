/**
 * Copyright (c) 2015-2026 Inera.
 * This file is part of the SKLTP project and software kit.
 * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import se.skltp.cooperation.Application;
import se.skltp.cooperation.domain.ServiceConsumer;
import se.skltp.cooperation.repository.ServiceConsumerRepository;
import se.skltp.cooperation.service.ServiceConsumerCriteria;

import com.querydsl.core.types.Predicate;

/**
 * Tests for {@link ServiceConsumerServiceImpl}
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ServiceConsumerServiceImplTest {

	private ServiceConsumerServiceImpl uut;
	@Mock
	private ServiceConsumerRepository serviceConsumerRepositoryMock;
	private ServiceConsumer sc1;
	private ServiceConsumer sc2;


	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		uut = new ServiceConsumerServiceImpl(serviceConsumerRepositoryMock);
	}

	@BeforeEach
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
