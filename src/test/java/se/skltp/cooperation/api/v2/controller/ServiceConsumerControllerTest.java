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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import se.skltp.cooperation.Application;
import se.skltp.cooperation.domain.ServiceConsumer;
import se.skltp.cooperation.service.ServiceConsumerCriteria;
import se.skltp.cooperation.service.ServiceConsumerService;
import se.skltp.cooperation.api.exception.ResourceNotFoundException;
import se.skltp.cooperation.api.v2.dto.ServiceConsumerDTO;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ServiceConsumerController REST controller.
 *
 * @author Peter Merikan
 * @see ServiceConsumerController
 */
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = SecurityConfig.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class ServiceConsumerControllerTest {

	@InjectMocks
	ServiceConsumerController uut;
	@MockitoBean
	private ServiceConsumerService serviceConsumerServiceMock;
	@MockitoBean
	private DozerBeanMapper mapperMock;
	private MockMvc mockMvc;

	private ServiceConsumer c1;
	private ServiceConsumer c2;
	private ServiceConsumerDTO dto1;
	private ServiceConsumerDTO dto2;


    @Autowired
    private WebApplicationContext wac;

	@PostConstruct
	public void setup() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(uut).build();
	}

	@BeforeEach
	public void setUpTestData() throws Exception {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilter(((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        })).build();

		c1 = new ServiceConsumer();
		c1.setId(1L);
		c2 = new ServiceConsumer();
		c2.setId(2L);
		dto1 = new ServiceConsumerDTO();
		dto1.setId(1L);
		dto1.setDescription("dto1.description");
		dto1.setHsaId("dto1.hsaId");
		dto2 = new ServiceConsumerDTO();
		dto2.setId(2L);
		dto2.setDescription("dto2.description");
		dto2.setHsaId("dto2.hsaId");

	}

	@Test
	public void getAllAsJson_shouldReturnAll() throws Exception {

		when(serviceConsumerServiceMock.findAll(any(ServiceConsumerCriteria.class))).thenReturn(Arrays.asList(c1, c2));
		when(mapperMock.map(c1, ServiceConsumerDTO.class)).thenReturn(dto1);
		when(mapperMock.map(c2, ServiceConsumerDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/api/v2/serviceConsumers").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(dto1.getId().intValue())))
			.andExpect(jsonPath("$.[0].description").value(is(dto1.getDescription())))
			.andExpect(jsonPath("$.[0].hsaId").value(is(dto1.getHsaId())))
			.andExpect(jsonPath("$.[1].id").value(is(dto2.getId().intValue())))
			.andExpect(jsonPath("$.[1].description").value(is(dto2.getDescription())))
			.andExpect(jsonPath("$.[1].hsaId").value(is(dto2.getHsaId())));

		verify(serviceConsumerServiceMock, times(1)).findAll(any(ServiceConsumerCriteria.class));
		verifyNoMoreInteractions(serviceConsumerServiceMock);

	}

	@Test
	public void getAllAsJson_shouldReturnWithFilter() throws Exception {

		when(serviceConsumerServiceMock.findAll(any(ServiceConsumerCriteria.class))).thenReturn(Arrays.asList(c1, c2));
		when(mapperMock.map(c1, ServiceConsumerDTO.class)).thenReturn(dto1);
		when(mapperMock.map(c2, ServiceConsumerDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/api/v2/serviceConsumers?connectionPointId=1").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(dto1.getId().intValue())))
			.andExpect(jsonPath("$.[1].id").value(is(dto2.getId().intValue())));

		verify(serviceConsumerServiceMock, times(1)).findAll(any(ServiceConsumerCriteria.class));
		verifyNoMoreInteractions(serviceConsumerServiceMock);

	}

	@Test
	public void getAllAsXml_shouldReturnAll() throws Exception {

		when(serviceConsumerServiceMock.findAll(any(ServiceConsumerCriteria.class))).thenReturn(Arrays.asList(c1, c2));
		when(mapperMock.map(c1, ServiceConsumerDTO.class)).thenReturn(dto1);
		when(mapperMock.map(c2, ServiceConsumerDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/api/v2/serviceConsumers").accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[1]/id").string(is(dto1.getId().toString())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[1]/description").string(is(dto1.getDescription())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[1]/hsaId").string(is(dto1.getHsaId())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[2]/id").string(is(dto2.getId().toString())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[2]/description").string(is(dto2.getDescription())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[2]/hsaId").string(is(dto2.getHsaId())));

		verify(serviceConsumerServiceMock, times(1)).findAll(any(ServiceConsumerCriteria.class));
		verifyNoMoreInteractions(serviceConsumerServiceMock);

	}

	@Test
	public void getAllAsXml_shouldReturnWithFilter() throws Exception {

		when(serviceConsumerServiceMock.findAll(any(ServiceConsumerCriteria.class))).thenReturn(Arrays.asList(c1, c2));
		when(mapperMock.map(c1, ServiceConsumerDTO.class)).thenReturn(dto1);
		when(mapperMock.map(c2, ServiceConsumerDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/api/v2/serviceConsumers?connectionPointId=1").accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[1]/id").string(is(dto1.getId().toString())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[1]/description").string(is(dto1.getDescription())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[1]/hsaId").string(is(dto1.getHsaId())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[2]/id").string(is(dto2.getId().toString())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[2]/description").string(is(dto2.getDescription())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[2]/hsaId").string(is(dto2.getHsaId())));

		verify(serviceConsumerServiceMock, times(1)).findAll(any(ServiceConsumerCriteria.class));
		verifyNoMoreInteractions(serviceConsumerServiceMock);

	}


	@Test
	public void getAllAsJson_shouldReturnEmptyList() throws Exception {

		when(serviceConsumerServiceMock.findAll()).thenReturn(new ArrayList<ServiceConsumer>());

		mockMvc.perform(get("/api/v2/serviceConsumers").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void getAllAsXml_shouldReturnEmptyList() throws Exception {

		when(serviceConsumerServiceMock.findAll()).thenReturn(new ArrayList<ServiceConsumer>());

		mockMvc.perform(get("/api/v2/serviceConsumers").accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/serviceConsumers").nodeCount(1))
			.andExpect(xpath("/serviceConsumers/*").nodeCount(0));
	}

	@Test
	public void get_shouldReturnOneAsJson() throws Exception {

		when(serviceConsumerServiceMock.find(c1.getId())).thenReturn(c1);
		when(mapperMock.map(c1, ServiceConsumerDTO.class)).thenReturn(dto1);

		mockMvc.perform(get("/api/v2/serviceConsumers/{id}", c1.getId())
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$.id").value(is(dto1.getId().intValue())))
			.andExpect(jsonPath("$.description").value(is(dto1.getDescription())))
			.andExpect(jsonPath("$.hsaId").value(is(dto1.getHsaId())));

	}

	@Test
	public void get_shouldReturnOneAsXml() throws Exception {

		when(serviceConsumerServiceMock.find(c1.getId())).thenReturn(c1);
		when(mapperMock.map(c1, ServiceConsumerDTO.class)).thenReturn(dto1);

		mockMvc.perform(get("/api/v2/serviceConsumers/{id}", c1.getId())
			.accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/serviceConsumer/id").string(is(dto1.getId().toString())))
			.andExpect(xpath("/serviceConsumer/description").string(is(dto1.getDescription())))
			.andExpect(xpath("/serviceConsumer/hsaId").string(is(dto1.getHsaId())));

	}

	@Test
	public void get_shouldThrowNotFoundException() throws Exception {

		when(serviceConsumerServiceMock.find(anyLong())).thenReturn(null);

		mockMvc.perform(get("/api/v2/serviceConsumers/{id}", Long.MAX_VALUE)
		  	      .contentType(MediaType.APPLICATION_JSON))
		  	      .andExpect(status().isNotFound())
		  	      .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException)
		  	      );

	}


}

