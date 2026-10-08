/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.service.impl;

import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import se.skltp.cooperation.Application;
import se.skltp.cooperation.domain.Cooperation;
import se.skltp.cooperation.repository.CooperationRepository;
import se.skltp.cooperation.service.CooperationCriteria;
import se.skltp.cooperation.service.CooperationCriteriaBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Application.class)
@WebAppConfiguration
class CooperationServiceImplTest {

	private CooperationServiceImpl uut;

	@Mock
	private CooperationRepository cooperationRepositoryMock;

	AutoCloseable mock;

	@BeforeEach
	void setUp() {
		mock = MockitoAnnotations.openMocks(this);
		uut = new CooperationServiceImpl(cooperationRepositoryMock);
	}

	@AfterEach
	void tearDown() throws Exception {
		mock.close();
	}

	@Test
	void findAll_shouldReturnAll() {
		Cooperation c1 = new Cooperation();
		c1.setId(1L);
		Cooperation c2 = new Cooperation();
		c2.setId(2L);
		when(cooperationRepositoryMock.findAll()).thenReturn(Arrays.asList(c1, c2));
		List<Cooperation> result = uut.findAll(new CooperationCriteria());
		assertEquals(2, result.size());
		assertEquals(1, result.getFirst().getId().longValue());
		assertEquals(2, result.get(1).getId().longValue());
		verify(cooperationRepositoryMock, times(1)).findAll();
	}

	@Test
	void findAll_withPredicateShouldReturnAll() {
		CooperationCriteria criteria = new CooperationCriteria();
		criteria.setConnectionPointId(1L);
		Cooperation c1 = new Cooperation();
		c1.setId(1L);
		Cooperation c2 = new Cooperation();
		c2.setId(2L);
		when(cooperationRepositoryMock.findAll(any(Predicate.class))).thenReturn(Arrays.asList(c1, c2));
		List<Cooperation> result = uut.findAll(criteria);
		assertEquals(2, result.size());
		assertEquals(1, result.getFirst().getId().longValue());
		assertEquals(2, result.get(1).getId().longValue());
		verify(cooperationRepositoryMock, times(1)).findAll(any(Predicate.class));
	}

	@Test
	void findAll_shouldReturnEmptyList() {

		when(cooperationRepositoryMock.findAll()).thenReturn(new ArrayList<>());
		List<Cooperation> result = uut.findAll(new CooperationCriteria());
		assertEquals(0, result.size());
	}

	@Test
	void find_shouldReturnOne() {
		Cooperation c1 = new Cooperation();
		c1.setId(1L);
		Optional<Cooperation> oc1 = Optional.of(c1);
		when(cooperationRepositoryMock.findById(c1.getId())).thenReturn(oc1);
		Cooperation result = uut.find(c1.getId());
		assertEquals(1, result.getId().longValue());
	}

	@Test
	void find_shouldReturnNullWhenNotFound() {
		Cooperation c1 = new Cooperation();
		c1.setId(1L);
		Optional<Cooperation> oc1 = Optional.empty();
		when(cooperationRepositoryMock.findById(c1.getId())).thenReturn(oc1);
		Cooperation result = uut.find(c1.getId());
		assertNull(result);
		verify(cooperationRepositoryMock, times(1)).findById(c1.getId());
	}

	@Test
	void buildPredicate_shouldBuild() {
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
		assertThat(predicate.toString(), is("cooperation.serviceConsumer.id = 1 && cooperation.logicalAddress.id = 2 && cooperation.serviceContract.id = 3"));

		predicate = uut.buildPredicate(new CooperationCriteriaBuilder()
			.serviceConsumerId(1L)
			.logicalAddressId(2L)
			.serviceContractId(3L)
			.connectionPointId(4L).build());
		assertThat(predicate.toString(), is("cooperation.serviceConsumer.id = 1 && cooperation.logicalAddress.id = 2 && cooperation.serviceContract.id = 3 && cooperation.connectionPoint.id = 4"));
	}

	@Test
	void buildCriteria_shouldReturnNull() {

		Predicate predicate = uut.buildPredicate(new CooperationCriteria());
		assertNull(predicate);
	}
}
