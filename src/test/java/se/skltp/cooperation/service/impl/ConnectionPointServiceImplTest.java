package se.skltp.cooperation.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import se.skltp.cooperation.Application;
import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.repository.ConnectionPointRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Peter Merikan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ConnectionPointServiceImplTest {

	private ConnectionPointServiceImpl uut;
	@Mock
	private ConnectionPointRepository connectionPointRepositoryMock;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		uut = new ConnectionPointServiceImpl(connectionPointRepositoryMock);
	}

	@Test
	public void findAll_shouldReturnAll() throws Exception {

		ConnectionPoint cp1 = new ConnectionPoint();
		cp1.setId(1L);
		ConnectionPoint cp2 = new ConnectionPoint();
		cp2.setId(2L);
		when(connectionPointRepositoryMock.findAll()).thenReturn(Arrays.asList(cp1, cp2));
		List<ConnectionPoint> result = uut.findAll();
		assertEquals(2, result.size());
		assertEquals(1L, result.get(0).getId().longValue());
		assertEquals(2L, result.get(1).getId().longValue());
		verify(connectionPointRepositoryMock, times(1)).findAll();

	}

	@Test
	public void findAll_shouldReturnEmpyList() throws Exception {

		when(connectionPointRepositoryMock.findAll()).thenReturn(new ArrayList<ConnectionPoint>());
		List<ConnectionPoint> result = uut.findAll();
		assertEquals(0, result.size());

	}

	@Test
	public void find_shouldReturnOne() throws Exception {
		ConnectionPoint cp = new ConnectionPoint();
		cp.setId(1L);
		when(connectionPointRepositoryMock.findOne(cp.getId())).thenReturn(cp);
		ConnectionPoint result = uut.find(cp.getId());
		assertEquals(1L, result.getId().longValue());
	}

	@Test
	public void find_shouldReturnNullWhenNotFound() throws Exception {
		ConnectionPoint cp = new ConnectionPoint();
		cp.setId(1L);
		when(connectionPointRepositoryMock.findOne(cp.getId())).thenReturn(null);
		ConnectionPoint result = uut.find(cp.getId());
		assertNull(result);

	}
}
