package se.skltp.cooperation.service.impl;

import com.mysema.query.BooleanBuilder;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		List<Cooperation> result = uut.findAll();
		assertEquals(2, result.size());
		assertEquals(1, result.get(0).getId().longValue());
		assertEquals(2, result.get(1).getId().longValue());
		verify(cooperationRepositoryMock,times(1)).findAll();
	}

	@Test
	public void findAll_withPredicateShouldReturnAll() throws Exception {
		Predicate predicate = new BooleanBuilder();
		Cooperation c1 = new Cooperation();
		c1.setId(1L);
		Cooperation c2 = new Cooperation();
		c2.setId(2L);
		when(cooperationRepositoryMock.findAll(predicate)).thenReturn(Arrays.asList(c1, c2));
		List<Cooperation> result = uut.findAll(predicate);
		assertEquals(2, result.size());
		assertEquals(1, result.get(0).getId().longValue());
		assertEquals(2, result.get(1).getId().longValue());
		verify(cooperationRepositoryMock,times(1)).findAll(predicate);
	}

	@Test
	public void findAll_shouldReturnEmptyList() throws Exception {

		when(cooperationRepositoryMock.findAll()).thenReturn(new ArrayList<Cooperation>());
		List<Cooperation> result = uut.findAll();
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
		verify(cooperationRepositoryMock,times(1)).findOne(c1.getId());

	}
}
