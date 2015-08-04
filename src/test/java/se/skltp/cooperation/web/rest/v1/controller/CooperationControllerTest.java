package se.skltp.cooperation.web.rest.v1.controller;

import com.mysema.query.types.Predicate;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import se.skltp.cooperation.CooperationApplication;
import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.domain.Cooperation;
import se.skltp.cooperation.domain.LogicalAddress;
import se.skltp.cooperation.domain.ServiceConsumer;
import se.skltp.cooperation.domain.ServiceContract;
import se.skltp.cooperation.repository.CooperationRepository;
import se.skltp.cooperation.web.rest.v1.dto.cooperation.ConnectionPointDTO;
import se.skltp.cooperation.web.rest.v1.dto.cooperation.CooperationDTO;
import se.skltp.cooperation.web.rest.v1.dto.cooperation.LogicalAddressDTO;
import se.skltp.cooperation.web.rest.v1.dto.cooperation.ServiceConsumerDTO;
import se.skltp.cooperation.web.rest.v1.dto.cooperation.ServiceContractDTO;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

/**
 * Tests for {@link CooperationController}
 *
 * @author Peter Merikan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CooperationApplication.class)
@WebAppConfiguration
public class CooperationControllerTest {

	@InjectMocks
	CooperationController uut;
	private Cooperation c1;
	private Cooperation c2;
	private CooperationDTO dto1;
	private CooperationDTO dto2;
	@Mock
	private CooperationRepository cooperationRepositoryMock;
	@Mock
	private DozerBeanMapper mapperMock;
	private MockMvc mockMvc;

	@PostConstruct
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(uut).build();
	}

	@Before
	public void setUpTestData() throws Exception {
		c1 = new Cooperation();
		c1.setId(1L);
		c2 = new Cooperation();
		c2.setId(2L);
		dto1 = new CooperationDTO();
		dto1.setId(1L);
		dto1.setConnectionPoint(new ConnectionPointDTO());
		dto1.getConnectionPoint().setId(11L);
		dto1.getConnectionPoint().setPlatform("dto1.connectionPoint.platform");
		dto1.setLogicalAddress(new LogicalAddressDTO());
		dto1.getLogicalAddress().setId(12L);
		dto1.getLogicalAddress().setDescription("dto1.logicalAddress.description");
		dto1.setServiceConsumer(new ServiceConsumerDTO());
		dto1.getServiceConsumer().setId(13L);
		dto1.getServiceConsumer().setHsaId("dto1.serviceConsumer.hsaId");
		dto1.setServiceContract(new ServiceContractDTO());
		dto1.getServiceContract().setId(14L);
		dto1.getServiceContract().setName("dto1.serviceContract.name");
		dto2 = new CooperationDTO();
		dto2.setId(2L);
		dto2.setConnectionPoint(new ConnectionPointDTO());
		dto2.getConnectionPoint().setId(21L);
		dto2.getConnectionPoint().setPlatform("dto2.connectionPoint.platform");
		dto2.setLogicalAddress(new LogicalAddressDTO());
		dto2.getLogicalAddress().setId(22L);
		dto2.getLogicalAddress().setDescription("dto2.logicalAddress.description");
		dto2.setServiceConsumer(new ServiceConsumerDTO());
		dto2.getServiceConsumer().setId(23L);
		dto2.getServiceConsumer().setHsaId("dto2.serviceConsumer.hsaId");
		dto2.setServiceContract(new ServiceContractDTO());
		dto2.getServiceContract().setId(24L);
		dto2.getServiceContract().setName("dto2.serviceContract.name");


	}

	@Test
	public void getAllAsJson_shouldReturnAll() throws Exception {

		when(cooperationRepositoryMock.findAll()).thenReturn(Arrays.asList(c1, c2));
		when(mapperMock.map(c1, CooperationDTO.class)).thenReturn(dto1);
		when(mapperMock.map(c2, CooperationDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/v1/cooperations").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(dto1.getId().intValue())))
			.andExpect(jsonPath("$.[1].id").value(is(dto2.getId().intValue())));

		verify(cooperationRepositoryMock, times(1)).findAll();
		verifyNoMoreInteractions(cooperationRepositoryMock);

	}

	@Test
	public void getAllAsJson_shouldReturnWithFilter() throws Exception {

		when(cooperationRepositoryMock.findAll(any(Predicate.class))).thenReturn(Arrays.asList(c1, c2));
		when(mapperMock.map(c1, CooperationDTO.class)).thenReturn(dto1);
		when(mapperMock.map(c2, CooperationDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/v1/cooperations?serviceConsumerId=1&logicalAddressId=2").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(dto1.getId().intValue())))
			.andExpect(jsonPath("$.[1].id").value(is(dto2.getId().intValue())));

		verify(cooperationRepositoryMock, times(1)).findAll(any(Predicate.class));
		verifyNoMoreInteractions(cooperationRepositoryMock);

	}

	@Test
	public void getAllAsJson_shouldReturnWithInclude() throws Exception {

		when(cooperationRepositoryMock.findAll()).thenReturn(Arrays.asList(c1, c2));
		when(mapperMock.map(c1, CooperationDTO.class)).thenReturn(dto1);
		when(mapperMock.map(c2, CooperationDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/v1/cooperations?include?connectionPoint, serviceConsumer   , logicalAddress, serviceContract")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(dto1.getId().intValue())))
			.andExpect(jsonPath("$.[0].connectionPoint.platform").value(is(dto1.getConnectionPoint().getPlatform())))
			.andExpect(jsonPath("$.[0].logicalAddress.description").value(is(dto1.getLogicalAddress().getDescription())))
			.andExpect(jsonPath("$.[0].serviceConsumer.hsaId").value(is(dto1.getServiceConsumer().getHsaId())))
			.andExpect(jsonPath("$.[0].serviceContract.name").value(is(dto1.getServiceContract().getName())))
			.andExpect(jsonPath("$.[1].id").value(is(dto2.getId().intValue())))
			.andExpect(jsonPath("$.[1].connectionPoint.platform").value(is(dto2.getConnectionPoint().getPlatform())))
			.andExpect(jsonPath("$.[1].logicalAddress.description").value(is(dto2.getLogicalAddress().getDescription())))
			.andExpect(jsonPath("$.[1].serviceConsumer.hsaId").value(is(dto2.getServiceConsumer().getHsaId())))
			.andExpect(jsonPath("$.[1].serviceContract.name").value(is(dto2.getServiceContract().getName())));

		verify(cooperationRepositoryMock, times(1)).findAll();
		verifyNoMoreInteractions(cooperationRepositoryMock);

	}

	@Test
	public void testGetAllAsXml_shouldReturnAll() throws Exception {

		when(cooperationRepositoryMock.findAll()).thenReturn(Arrays.asList(c1, c2));
		when(mapperMock.map(c1, CooperationDTO.class)).thenReturn(dto1);
		when(mapperMock.map(c2, CooperationDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/v1/cooperations").accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML))
			.andExpect(xpath("/cooperations/cooperation[1]/id").string(is(dto1.getId().toString())))
			.andExpect(xpath("/cooperations/cooperation[2]/id").string(is(dto2.getId().toString())));

		verify(cooperationRepositoryMock, times(1)).findAll();
		verifyNoMoreInteractions(cooperationRepositoryMock);

	}

	@Test
	public void testGetAllAsXml_shouldReturnWithInclude() throws Exception {

		when(cooperationRepositoryMock.findAll()).thenReturn(Arrays.asList(c1, c2));
		when(mapperMock.map(c1, CooperationDTO.class)).thenReturn(dto1);
		when(mapperMock.map(c2, CooperationDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/v1/cooperations?include?connectionPoint, serviceConsumer   , logicalAddress, serviceContract")
			.accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML))
			.andExpect(xpath("/cooperations/cooperation[1]/id").string(is(dto1.getId().toString())))
			.andExpect(xpath("/cooperations/cooperation[1]/connectionPoint/platform").string(is(dto1.getConnectionPoint().getPlatform())))
			.andExpect(xpath("/cooperations/cooperation[1]/logicalAddress/description").string(is(dto1.getLogicalAddress().getDescription())))
			.andExpect(xpath("/cooperations/cooperation[1]/serviceConsumer/hsaId").string(is(dto1.getServiceConsumer().getHsaId())))
			.andExpect(xpath("/cooperations/cooperation[1]/serviceContract/name").string(is(dto1.getServiceContract().getName())))
			.andExpect(xpath("/cooperations/cooperation[2]/id").string(is(dto2.getId().toString())))
			.andExpect(xpath("/cooperations/cooperation[2]/connectionPoint/platform").string(is(dto2.getConnectionPoint().getPlatform())))
			.andExpect(xpath("/cooperations/cooperation[2]/logicalAddress/description").string(is(dto2.getLogicalAddress().getDescription())))
			.andExpect(xpath("/cooperations/cooperation[2]/serviceConsumer/hsaId").string(is(dto2.getServiceConsumer().getHsaId())))
			.andExpect(xpath("/cooperations/cooperation[2]/serviceContract/name").string(is(dto2.getServiceContract().getName())));


		verify(cooperationRepositoryMock, times(1)).findAll();
		verifyNoMoreInteractions(cooperationRepositoryMock);

	}

	@Test
	public void getAllAsJson_shouldReturnEmptyList() throws Exception {

		when(cooperationRepositoryMock.findAll()).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/v1/cooperations").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void getAllAsXml_shouldReturnEmptyList() throws Exception {

		when(cooperationRepositoryMock.findAll()).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/v1/cooperations").accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML))
			.andExpect(xpath("/cooperations").nodeCount(1)).
			andExpect(xpath("/cooperations/cooperation").nodeCount(0));
	}

	@Test
	public void get_shouldReturnOneAsJson() throws Exception {

		when(cooperationRepositoryMock.findOne(c1.getId())).thenReturn(c1);
		when(mapperMock.map(c1, CooperationDTO.class)).thenReturn(dto1);

		mockMvc.perform(get("/v1/cooperations/{id}", c1.getId())
			.accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(dto1.getId().intValue()));
	}

	@Test
	public void get_shouldReturnOneAsXml() throws Exception {

		when(cooperationRepositoryMock.findOne(c1.getId())).thenReturn(c1);
		when(mapperMock.map(c1, CooperationDTO.class)).thenReturn(dto1);

		mockMvc.perform(get("/v1/cooperations/{id}", c1.getId()).accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML))
			.andExpect(xpath("/cooperation/id").string(is(dto1.getId().toString())));
	}

	@Test
	public void get_shouldReturnNotFound() throws Exception {

		when(cooperationRepositoryMock.findOne(anyLong())).thenReturn(null);

		mockMvc.perform(get("/v1/cooperations/{id}", Long.MAX_VALUE))
			.andExpect(status().isNotFound());
	}

	@Test
	public void buildCriteria_shouldBuild() throws Exception {

		Predicate predicate = uut.buildCriteria(1L, null, null, null);
		assertThat(predicate.toString(), is("cooperation.serviceConsumer.id = 1"));
		predicate = uut.buildCriteria(1L, 2L, null, null);
		assertThat(predicate.toString(), is("cooperation.serviceConsumer.id = 1 && cooperation.logicalAddress.id = 2"));
		predicate = uut.buildCriteria(1L, 2L, 3L, null);
		assertThat(predicate.toString(), is("cooperation.serviceConsumer.id = 1 && cooperation.logicalAddress.id = 2 && cooperation.serviceContract.id = 3"));
		predicate = uut.buildCriteria(1L, 2L, 3L, 4L);
		assertThat(predicate.toString(), is("cooperation.serviceConsumer.id = 1 && cooperation.logicalAddress.id = 2 && cooperation.serviceContract.id = 3 && cooperation.connectionPoint.id = 4"));

	}

	@Test
	public void buildCriteria_shouldReturnNull() throws Exception {

		Predicate predicate = uut.buildCriteria(null, null, null, null);
		assertNull(predicate);
	}


	@Test
	public void testIncludeOrNot() throws Exception {
		Cooperation c = new Cooperation();
		c.setConnectionPoint(new ConnectionPoint());
		c.setLogicalAddress(new LogicalAddress());
		c.setServiceConsumer(new ServiceConsumer());
		c.setServiceContract(new ServiceContract());
		uut.includeOrNot(Arrays.asList(uut.INCLUDE_LOGICALADDRESS, uut.INCLUDE_SERVICECONSUMER), c);
		assertNotNull(c.getLogicalAddress());
		assertNotNull(c.getServiceConsumer());
		assertNull(c.getConnectionPoint());
		assertNull(c.getServiceContract());
	}
}
