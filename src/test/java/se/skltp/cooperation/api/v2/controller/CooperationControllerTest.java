/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.v2.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import se.skltp.cooperation.Application;
import se.skltp.cooperation.domain.*;
import se.skltp.cooperation.service.CooperationCriteria;
import se.skltp.cooperation.service.CooperationService;
import se.skltp.cooperation.api.exception.ResourceNotFoundException;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for {@link CooperationController}
 *
 */
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@WebAppConfiguration
class CooperationControllerTest {

	@MockitoBean
	private CooperationService cooperationServiceMock;

	private MockMvc mockMvc;
	private Cooperation coop1;
	private Cooperation coop2;

    @Autowired
    private WebApplicationContext webAppCtx;

	@InjectMocks
	CooperationController coopCtrl;
	private AutoCloseable mocks;

	@BeforeEach
	void setUpTestData() {

		// Spring Boot 4 removed MockitoTestExecutionListener, which used to initialise
		// @InjectMocks fields for us. Same pattern as ServiceConsumerControllerTest.
		mocks = MockitoAnnotations.openMocks(this);

		this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppCtx).addFilter(((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        })).build();

		// Building Cooperation 1 fixture
		coop1 = new Cooperation();
		coop1.setId(1L);

		ConnectionPoint conn1 = new ConnectionPoint();
		conn1.setId(11L);
		conn1.setPlatform("dto1.connectionPoint.platform");

		LogicalAddress la1 = new LogicalAddress();
		la1.setId(12L);
		la1.setDescription("dto1.logicalAddress.description");

		ServiceConsumer sc1 = new ServiceConsumer();
		sc1.setId(13L);
		sc1.setHsaId("dto1.serviceConsumer.hsaId");
		sc1.setConnectionPoint(conn1);

		ServiceContract contract1 = new ServiceContract();
		contract1.setId(14L);
		contract1.setName("dto1.serviceContract.name");

		coop1.setConnectionPoint(conn1);
		coop1.setLogicalAddress(la1);
		coop1.setServiceConsumer(sc1);
		coop1.setServiceContract(contract1);

		// Building Cooperation 2 fixture
		coop2 = new Cooperation();
		coop2.setId(2L);

		ConnectionPoint conn2 = new ConnectionPoint();
		conn2.setId(21L);
		conn2.setPlatform("dto2.connectionPoint.platform");

		LogicalAddress la2 = new LogicalAddress();
		la2.setId(22L);
		la2.setDescription("dto2.logicalAddress.description");

		ServiceConsumer sc2 = new ServiceConsumer();
		sc2.setId(23L);
		sc2.setHsaId("dto2.serviceConsumer.hsaId");
		sc2.setConnectionPoint(conn1);

		ServiceContract contract2 = new ServiceContract();
		contract2.setId(24L);
		contract2.setName("dto2.serviceContract.name");

		coop2.setConnectionPoint(conn2);
		coop2.setLogicalAddress(la2);
		coop2.setServiceConsumer(sc2);
		coop2.setServiceContract(contract2);
	}

	@AfterEach
	void tearDown() throws Exception {
		mocks.close();
	}

	@Test
	void getAllAsJson_shouldReturnAll() throws Exception {

		when(cooperationServiceMock.findAll(any(CooperationCriteria.class))).thenReturn(Arrays.asList(coop1, coop2));

		mockMvc.perform(get("/api/v2/cooperations").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(coop1.getId().intValue())))
			.andExpect(jsonPath("$.[1].id").value(is(coop2.getId().intValue())));

		verify(cooperationServiceMock, times(1)).findAll(any(CooperationCriteria.class));
		verifyNoMoreInteractions(cooperationServiceMock);
	}

	@Test
	void getAllAsJson_shouldReturnWithFilter() throws Exception {

		when(cooperationServiceMock.findAll(any(CooperationCriteria.class))).thenReturn(Arrays.asList(coop1, coop2));

		mockMvc.perform(get("/api/v2/cooperations?serviceConsumerId=1&logicalAddressId=2").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(coop1.getId().intValue())))
			.andExpect(jsonPath("$.[1].id").value(is(coop2.getId().intValue())));

		verify(cooperationServiceMock, times(1)).findAll(any(CooperationCriteria.class));
		verifyNoMoreInteractions(cooperationServiceMock);
	}

	@Test
	void getAllAsJson_shouldReturnWithInclude() throws Exception {

		when(cooperationServiceMock.findAll(any(CooperationCriteria.class))).thenReturn(Arrays.asList(coop1, coop2));

		mockMvc.perform(get("/api/v2/cooperations")
				.param("include", "connectionPoint,serviceConsumer,logicalAddress,serviceContract")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(coop1.getId().intValue())))
			.andExpect(jsonPath("$.[0].connectionPoint.platform").value(is(coop1.getConnectionPoint().getPlatform())))
			.andExpect(jsonPath("$.[0].logicalAddress.description").value(is(coop1.getLogicalAddress().getDescription())))
			.andExpect(jsonPath("$.[0].serviceConsumer.hsaId").value(is(coop1.getServiceConsumer().getHsaId())))
			.andExpect(jsonPath("$.[0].serviceContract.name").value(is(coop1.getServiceContract().getName())))
			.andExpect(jsonPath("$.[1].id").value(is(coop2.getId().intValue())))
			.andExpect(jsonPath("$.[1].connectionPoint.platform").value(is(coop2.getConnectionPoint().getPlatform())))
			.andExpect(jsonPath("$.[1].logicalAddress.description").value(is(coop2.getLogicalAddress().getDescription())))
			.andExpect(jsonPath("$.[1].serviceConsumer.hsaId").value(is(coop2.getServiceConsumer().getHsaId())))
			.andExpect(jsonPath("$.[1].serviceContract.name").value(is(coop2.getServiceContract().getName())));

		verify(cooperationServiceMock, times(1)).findAll(any(CooperationCriteria.class));
		verifyNoMoreInteractions(cooperationServiceMock);
	}

	@Test
	void testGetAllAsXml_shouldReturnAll() throws Exception {

		when(cooperationServiceMock.findAll(any(CooperationCriteria.class))).thenReturn(Arrays.asList(coop1, coop2));

		mockMvc.perform(get("/api/v2/cooperations").accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/cooperations/cooperation[1]/id").string(is(coop1.getId().toString())))
			.andExpect(xpath("/cooperations/cooperation[2]/id").string(is(coop2.getId().toString())));

		verify(cooperationServiceMock, times(1)).findAll(any(CooperationCriteria.class));
		verifyNoMoreInteractions(cooperationServiceMock);
	}

	@Test
	void testGetAllAsXml_shouldReturnWithInclude() throws Exception {

		when(cooperationServiceMock.findAll(any(CooperationCriteria.class))).thenReturn(Arrays.asList(coop1, coop2));

		mockMvc.perform(get("/api/v2/cooperations")
				.param("include", "connectionPoint,serviceConsumer,logicalAddress,serviceContract")
			.accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/cooperations/cooperation[1]/id").string(is(coop1.getId().toString())))
			.andExpect(xpath("/cooperations/cooperation[1]/connectionPoint/platform").string(is(coop1.getConnectionPoint().getPlatform())))
			.andExpect(xpath("/cooperations/cooperation[1]/logicalAddress/description").string(is(coop1.getLogicalAddress().getDescription())))
			.andExpect(xpath("/cooperations/cooperation[1]/serviceConsumer/hsaId").string(is(coop1.getServiceConsumer().getHsaId())))
			.andExpect(xpath("/cooperations/cooperation[1]/serviceContract/name").string(is(coop1.getServiceContract().getName())))
			.andExpect(xpath("/cooperations/cooperation[2]/id").string(is(coop2.getId().toString())))
			.andExpect(xpath("/cooperations/cooperation[2]/connectionPoint/platform").string(is(coop2.getConnectionPoint().getPlatform())))
			.andExpect(xpath("/cooperations/cooperation[2]/logicalAddress/description").string(is(coop2.getLogicalAddress().getDescription())))
			.andExpect(xpath("/cooperations/cooperation[2]/serviceConsumer/hsaId").string(is(coop2.getServiceConsumer().getHsaId())))
			.andExpect(xpath("/cooperations/cooperation[2]/serviceContract/name").string(is(coop2.getServiceContract().getName())));

		verify(cooperationServiceMock, times(1)).findAll(any(CooperationCriteria.class));
		verifyNoMoreInteractions(cooperationServiceMock);
	}

	@Test
	void get_shouldReturnOneAsJson() throws Exception {

		when(cooperationServiceMock.find(coop1.getId())).thenReturn(coop1);

		mockMvc.perform(get("/api/v2/cooperations/{id}", coop1.getId())
			.accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$.id").value(coop1.getId().intValue()));
	}

	@Test
	void get_shouldReturnOneAsXml() throws Exception {

		when(cooperationServiceMock.find(coop1.getId())).thenReturn(coop1);

		mockMvc.perform(get("/api/v2/cooperations/{id}", coop1.getId()).accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/cooperation/id").string(is(coop1.getId().toString())));
	}

	@Test
	void get_shouldThrowNotFoundException() throws Exception {

		when(cooperationServiceMock.find(anyLong())).thenReturn(null);

		mockMvc.perform(get("/api/v2/cooperations/{id}", Long.MAX_VALUE)
	    	      .contentType(MediaType.APPLICATION_JSON))
	    	      .andExpect(status().isNotFound())
	    	      .andExpect(result -> assertInstanceOf(ResourceNotFoundException.class, result.getResolvedException())
	    	      );
	}

	@Test
	void buildCriteria_shouldBeEmpty() {

		CooperationCriteria criteria = new CooperationCriteria(null,
			null,
			null,
			null,
			null);
		assertTrue(criteria.isEmpty());
	}

	@Test
	void testIncludeOrNot() {
		Cooperation c = new Cooperation();
		c.setConnectionPoint(new ConnectionPoint());
		c.setLogicalAddress(new LogicalAddress());
		c.setServiceConsumer(new ServiceConsumer());
		c.setServiceContract(new ServiceContract());
		coopCtrl.includeOrNot(Arrays.asList(CooperationController.INCLUDE_LOGICALADDRESS, CooperationController.INCLUDE_SERVICECONSUMER), c);
		assertNotNull(c.getLogicalAddress());
		assertNotNull(c.getServiceConsumer());
		assertNull(c.getConnectionPoint());
		assertNull(c.getServiceContract());
	}
}
