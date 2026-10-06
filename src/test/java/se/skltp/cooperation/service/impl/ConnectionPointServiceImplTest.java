/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import se.skltp.cooperation.Application;
import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.repository.ConnectionPointRepository;
import se.skltp.cooperation.service.ConnectionPointCriteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Application.class)
@WebAppConfiguration
class ConnectionPointServiceImplTest {

	private ConnectionPointServiceImpl uut;

	@Mock
	private ConnectionPointRepository connectionPointRepositoryMock;
	AutoCloseable mocks;

	@BeforeEach
	void setUp() {
		mocks = MockitoAnnotations.openMocks(this);
		uut = new ConnectionPointServiceImpl(connectionPointRepositoryMock);
	}

	@AfterEach
	void tearDown() throws Exception {
		mocks.close();
	}

	@Test
	void findAll_shouldReturnAll() {

		ConnectionPoint cp1 = new ConnectionPoint();
		cp1.setId(1L);
		ConnectionPoint cp2 = new ConnectionPoint();
		cp2.setId(2L);
		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null,null,null,null,null,null);
		when(connectionPointRepositoryMock.findAll()).thenReturn(Arrays.asList(cp1, cp2));
		List<ConnectionPoint> result = uut.findAll(criteria);
		assertEquals(2, result.size());
		assertEquals(1L, result.getFirst().getId().longValue());
		assertEquals(2L, result.get(1).getId().longValue());
		verify(connectionPointRepositoryMock, times(1)).findAll();
	}

	@Test
	void findAll_shouldReturnEmptyList() {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null,null,null,null,null,null);
		when(connectionPointRepositoryMock.findAll()).thenReturn(new ArrayList<>());
		List<ConnectionPoint> result = uut.findAll(criteria);
		assertEquals(0, result.size());
	}

	@Test
	void find_shouldReturnOne() {
		ConnectionPoint cp = new ConnectionPoint();
		cp.setId(1L);
		Optional<ConnectionPoint> ocp = Optional.of(cp);
		when(connectionPointRepositoryMock.findById(cp.getId())).thenReturn(ocp);
		ConnectionPoint result = uut.find(cp.getId());
		assertEquals(1L, result.getId().longValue());
	}

	@Test
	void find_shouldReturnNullWhenNotFound() {
		ConnectionPoint cp = new ConnectionPoint();
		cp.setId(1L);
		Optional<ConnectionPoint> ocp = Optional.empty();
		when(connectionPointRepositoryMock.findById(cp.getId())).thenReturn(ocp);
		ConnectionPoint result = uut.find(cp.getId());
		assertNull(result);
	}
}
