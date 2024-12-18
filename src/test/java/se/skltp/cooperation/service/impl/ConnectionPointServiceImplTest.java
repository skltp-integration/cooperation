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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

/**
 * @author Peter Merikan
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ConnectionPointServiceImplTest {

	private ConnectionPointServiceImpl uut;
	@Mock
	private ConnectionPointRepository connectionPointRepositoryMock;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		uut = new ConnectionPointServiceImpl(connectionPointRepositoryMock);
	}

	@Test
	public void findAll_shouldReturnAll() throws Exception {

		ConnectionPoint cp1 = new ConnectionPoint();
		cp1.setId(1L);
		ConnectionPoint cp2 = new ConnectionPoint();
		cp2.setId(2L);
		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null,null,null,null,null,null);
		when(connectionPointRepositoryMock.findAll()).thenReturn(Arrays.asList(cp1, cp2));
		List<ConnectionPoint> result = uut.findAll(criteria);
		assertEquals(2, result.size());
		assertEquals(1L, result.get(0).getId().longValue());
		assertEquals(2L, result.get(1).getId().longValue());
		verify(connectionPointRepositoryMock, times(1)).findAll();

	}

	@Test
	public void findAll_shouldReturnEmpyList() throws Exception {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null,null,null,null,null,null);
		when(connectionPointRepositoryMock.findAll()).thenReturn(new ArrayList<ConnectionPoint>());
		List<ConnectionPoint> result = uut.findAll(criteria);
		assertEquals(0, result.size());

	}

	@Test
	public void find_shouldReturnOne() throws Exception {
		ConnectionPoint cp = new ConnectionPoint();
		cp.setId(1L);
		Optional<ConnectionPoint> ocp = Optional.of(cp);
		when(connectionPointRepositoryMock.findById(cp.getId())).thenReturn(ocp);
		ConnectionPoint result = uut.find(cp.getId());
		assertEquals(1L, result.getId().longValue());
	}

	@Test
	public void find_shouldReturnNullWhenNotFound() throws Exception {
		ConnectionPoint cp = new ConnectionPoint();
		cp.setId(1L);
		Optional<ConnectionPoint> ocp = Optional.empty();
		when(connectionPointRepositoryMock.findById(cp.getId())).thenReturn(ocp);
		ConnectionPoint result = uut.find(cp.getId());
		assertNull(result);

	}
}
