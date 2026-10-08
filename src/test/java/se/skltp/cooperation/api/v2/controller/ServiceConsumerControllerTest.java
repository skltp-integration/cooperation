/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.v2.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import se.skltp.cooperation.Application;
import se.skltp.cooperation.domain.ServiceConsumer;
import se.skltp.cooperation.service.ServiceConsumerCriteria;
import se.skltp.cooperation.service.ServiceConsumerService;
import se.skltp.cooperation.api.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ServiceConsumerController REST controller.
 *
 * @see ServiceConsumerController
 */
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@WebAppConfiguration
class ServiceConsumerControllerTest {

	@MockitoBean
	private ServiceConsumerService serviceConsumerServiceMock;

	private MockMvc mockMvc;

	private ServiceConsumer cons1;
	private ServiceConsumer cons2;


    @Autowired
    private WebApplicationContext wac;

	@BeforeEach
	void setUpTestData() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilter(((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        })).build();

		// Consumer 1 fixture
		cons1 = new ServiceConsumer();
		cons1.setId(1L);
		cons1.setDescription("dto1.description");
		cons1.setHsaId("dto1.hsaId");

		// Consumer 2 fixture
		cons2 = new ServiceConsumer();
		cons2.setId(2L);
		cons2.setDescription("dto2.description");
		cons2.setHsaId("dto2.hsaId");
	}

	@Test
	void getAllAsJson_shouldReturnAll() throws Exception {

		when(serviceConsumerServiceMock.findAll(any(ServiceConsumerCriteria.class))).thenReturn(Arrays.asList(cons1, cons2));

		mockMvc.perform(get("/api/v2/serviceConsumers").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(cons1.getId().intValue())))
			.andExpect(jsonPath("$.[0].description").value(is(cons1.getDescription())))
			.andExpect(jsonPath("$.[0].hsaId").value(is(cons1.getHsaId())))
			.andExpect(jsonPath("$.[1].id").value(is(cons2.getId().intValue())))
			.andExpect(jsonPath("$.[1].description").value(is(cons2.getDescription())))
			.andExpect(jsonPath("$.[1].hsaId").value(is(cons2.getHsaId())));

		verify(serviceConsumerServiceMock, times(1)).findAll(any(ServiceConsumerCriteria.class));
		verifyNoMoreInteractions(serviceConsumerServiceMock);
	}

	@Test
	void getAllAsJson_shouldReturnWithFilter() throws Exception {

		when(serviceConsumerServiceMock.findAll(any(ServiceConsumerCriteria.class))).thenReturn(Arrays.asList(cons1, cons2));

		mockMvc.perform(get("/api/v2/serviceConsumers")
			.param("connectionPointId","1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(cons1.getId().intValue())))
			.andExpect(jsonPath("$.[1].id").value(is(cons2.getId().intValue())));

		verify(serviceConsumerServiceMock, times(1)).findAll(any(ServiceConsumerCriteria.class));
		verifyNoMoreInteractions(serviceConsumerServiceMock);
	}

	@Test
	void getAllAsXml_shouldReturnAll() throws Exception {

		when(serviceConsumerServiceMock.findAll(any(ServiceConsumerCriteria.class))).thenReturn(Arrays.asList(cons1, cons2));

		mockMvc.perform(get("/api/v2/serviceConsumers")
				.accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[1]/id").string(is(cons1.getId().toString())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[1]/description").string(is(cons1.getDescription())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[1]/hsaId").string(is(cons1.getHsaId())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[2]/id").string(is(cons2.getId().toString())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[2]/description").string(is(cons2.getDescription())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[2]/hsaId").string(is(cons2.getHsaId())));

		verify(serviceConsumerServiceMock, times(1)).findAll(any(ServiceConsumerCriteria.class));
		verifyNoMoreInteractions(serviceConsumerServiceMock);
	}

	@Test
	void getAllAsXml_shouldReturnWithFilter() throws Exception {

		when(serviceConsumerServiceMock.findAll(any(ServiceConsumerCriteria.class))).thenReturn(Arrays.asList(cons1, cons2));

		mockMvc.perform(get("/api/v2/serviceConsumers")
				.param("connectionPointId","1")
				.accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[1]/id").string(is(cons1.getId().toString())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[1]/description").string(is(cons1.getDescription())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[1]/hsaId").string(is(cons1.getHsaId())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[2]/id").string(is(cons2.getId().toString())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[2]/description").string(is(cons2.getDescription())))
			.andExpect(xpath("/serviceConsumers/serviceConsumer[2]/hsaId").string(is(cons2.getHsaId())));

		verify(serviceConsumerServiceMock, times(1)).findAll(any(ServiceConsumerCriteria.class));
		verifyNoMoreInteractions(serviceConsumerServiceMock);
	}

	@Test
	void getAllAsJson_shouldReturnEmptyList() throws Exception {

		when(serviceConsumerServiceMock.findAll()).thenReturn(new ArrayList<ServiceConsumer>());

		mockMvc.perform(get("/api/v2/serviceConsumers").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	void getAllAsXml_shouldReturnEmptyList() throws Exception {

		when(serviceConsumerServiceMock.findAll()).thenReturn(new ArrayList<ServiceConsumer>());

		mockMvc.perform(get("/api/v2/serviceConsumers").accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/serviceConsumers").nodeCount(1))
			.andExpect(xpath("/serviceConsumers/*").nodeCount(0));
	}

	@Test
	void get_shouldReturnOneAsJson() throws Exception {

		when(serviceConsumerServiceMock.find(cons1.getId())).thenReturn(cons1);

		mockMvc.perform(get("/api/v2/serviceConsumers/{id}", cons1.getId())
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$.id").value(is(cons1.getId().intValue())))
			.andExpect(jsonPath("$.description").value(is(cons1.getDescription())))
			.andExpect(jsonPath("$.hsaId").value(is(cons1.getHsaId())));
	}

	@Test
	void get_shouldReturnOneAsXml() throws Exception {

		when(serviceConsumerServiceMock.find(cons1.getId())).thenReturn(cons1);

		mockMvc.perform(get("/api/v2/serviceConsumers/{id}", cons1.getId())
			.accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/serviceConsumer/id").string(is(cons1.getId().toString())))
			.andExpect(xpath("/serviceConsumer/description").string(is(cons1.getDescription())))
			.andExpect(xpath("/serviceConsumer/hsaId").string(is(cons1.getHsaId())));
	}

	@Test
	void get_shouldThrowNotFoundException() throws Exception {

		when(serviceConsumerServiceMock.find(anyLong())).thenReturn(null);

		mockMvc.perform(get("/api/v2/serviceConsumers/{id}", Long.MAX_VALUE)
		  	      .contentType(MediaType.APPLICATION_JSON))
		  	      .andExpect(status().isNotFound())
		  	      .andExpect(result -> assertInstanceOf(ResourceNotFoundException.class, result.getResolvedException())
		  	      );
	}
}
