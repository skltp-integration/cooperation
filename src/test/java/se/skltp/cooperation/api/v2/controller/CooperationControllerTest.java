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
package se.skltp.cooperation.api.v2.controller;

import org.apache.catalina.security.SecurityConfig;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import se.skltp.cooperation.Application;
import se.skltp.cooperation.api.v2.dto.*;
import se.skltp.cooperation.domain.*;
import se.skltp.cooperation.service.CooperationCriteria;
import se.skltp.cooperation.service.CooperationService;
import se.skltp.cooperation.api.exception.ResourceNotFoundException;
import se.skltp.cooperation.api.v2.dto.*;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for {@link CooperationController}
 *
 * @author Peter Merikan
 */
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = SecurityConfig.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class CooperationControllerTest {


	@MockBean
	private CooperationService cooperationServiceMock;
	@MockBean
	private DozerBeanMapper mapperMock;
	private MockMvc mockMvc;
	private Cooperation c1;
	private Cooperation c2;
	private CooperationDTO dto1;
	private CooperationDTO dto2;


    @Autowired
    private WebApplicationContext wac;

	@InjectMocks
	CooperationController uut;

	@Before
	public void setUpTestData() throws Exception {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilter(((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        })).build();

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

		when(cooperationServiceMock.findAll(any(CooperationCriteria.class))).thenReturn(Arrays.asList(c1, c2));
		when(mapperMock.map(c1, CooperationDTO.class)).thenReturn(dto1);
		when(mapperMock.map(c2, CooperationDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/api/v2/cooperations").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(dto1.getId().intValue())))
			.andExpect(jsonPath("$.[1].id").value(is(dto2.getId().intValue())));

		verify(cooperationServiceMock, times(1)).findAll(any(CooperationCriteria.class));
		verifyNoMoreInteractions(cooperationServiceMock);

	}

	@Test
	public void getAllAsJson_shouldReturnWithFilter() throws Exception {

		when(cooperationServiceMock.findAll(any(CooperationCriteria.class))).thenReturn(Arrays.asList(c1, c2));
		when(mapperMock.map(c1, CooperationDTO.class)).thenReturn(dto1);
		when(mapperMock.map(c2, CooperationDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/api/v2/cooperations?serviceConsumerId=1&logicalAddressId=2").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(dto1.getId().intValue())))
			.andExpect(jsonPath("$.[1].id").value(is(dto2.getId().intValue())));

		verify(cooperationServiceMock, times(1)).findAll(any(CooperationCriteria.class));
		verifyNoMoreInteractions(cooperationServiceMock);

	}

	@Test
	public void getAllAsJson_shouldReturnWithInclude() throws Exception {

		when(cooperationServiceMock.findAll(any(CooperationCriteria.class))).thenReturn(Arrays.asList(c1, c2));
		when(mapperMock.map(c1, CooperationDTO.class)).thenReturn(dto1);
		when(mapperMock.map(c2, CooperationDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/api/v2/cooperations?include?connectionPoint,serviceConsumer,logicalAddress,serviceContract")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
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

		verify(cooperationServiceMock, times(1)).findAll(any(CooperationCriteria.class));
		verifyNoMoreInteractions(cooperationServiceMock);

	}

	@Test
	public void testGetAllAsXml_shouldReturnAll() throws Exception {

		when(cooperationServiceMock.findAll(any(CooperationCriteria.class))).thenReturn(Arrays.asList(c1, c2));
		when(mapperMock.map(c1, CooperationDTO.class)).thenReturn(dto1);
		when(mapperMock.map(c2, CooperationDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/api/v2/cooperations").accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/cooperations/cooperation[1]/id").string(is(dto1.getId().toString())))
			.andExpect(xpath("/cooperations/cooperation[2]/id").string(is(dto2.getId().toString())));

		verify(cooperationServiceMock, times(1)).findAll(any(CooperationCriteria.class));
		verifyNoMoreInteractions(cooperationServiceMock);

	}

	@Test
	public void testGetAllAsXml_shouldReturnWithInclude() throws Exception {

		when(cooperationServiceMock.findAll(any(CooperationCriteria.class))).thenReturn(Arrays.asList(c1, c2));
		when(mapperMock.map(c1, CooperationDTO.class)).thenReturn(dto1);
		when(mapperMock.map(c2, CooperationDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/api/v2/cooperations?include?connectionPoint,serviceConsumer,logicalAddress,serviceContract")
			.accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
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


		verify(cooperationServiceMock, times(1)).findAll(any(CooperationCriteria.class));
		verifyNoMoreInteractions(cooperationServiceMock);

	}


	@Test
	public void get_shouldReturnOneAsJson() throws Exception {

		when(cooperationServiceMock.find(c1.getId())).thenReturn(c1);
		when(mapperMock.map(c1, CooperationDTO.class)).thenReturn(dto1);

		mockMvc.perform(get("/api/v2/cooperations/{id}", c1.getId())
			.accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$.id").value(dto1.getId().intValue()));
	}

	@Test
	public void get_shouldReturnOneAsXml() throws Exception {

		when(cooperationServiceMock.find(c1.getId())).thenReturn(c1);
		when(mapperMock.map(c1, CooperationDTO.class)).thenReturn(dto1);

		mockMvc.perform(get("/api/v2/cooperations/{id}", c1.getId()).accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/cooperation/id").string(is(dto1.getId().toString())));
	}

	@Test
	public void get_shouldThrowNotFoundException() throws Exception {

		when(cooperationServiceMock.find(anyLong())).thenReturn(null);

		mockMvc.perform(get("/api/v2/cooperations/{id}", Long.MAX_VALUE)
	    	      .contentType(MediaType.APPLICATION_JSON))
	    	      .andExpect(status().isNotFound())
	    	      .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException)
	    	      );
	}


	@Test
	public void buildCriteria_shouldBeEmpty() throws Exception {

		CooperationCriteria criteria = new CooperationCriteria(null, null, null, null, null);
		assertTrue(criteria.isEmpty());
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
