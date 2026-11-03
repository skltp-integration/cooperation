/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.v2.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import se.skltp.cooperation.Application;
import se.skltp.cooperation.api.exception.ResourceNotFoundException;
import se.skltp.cooperation.domain.InstalledContract;
import se.skltp.cooperation.service.InstalledContractCriteria;
import se.skltp.cooperation.service.InstalledContractService;
import se.skltp.cooperation.api.v2.dto.InstalledContractDTO;

/**
 * Test class for the ConnectionPointController REST controller.
 *
 * @author Jan Vasternas
 * @see InstalledContractController
 */
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
class InstalledContractControllerTest {

	InstalledContract ic1;
	InstalledContract ic2;
	InstalledContractDTO dto1;
	InstalledContractDTO dto2;
	@MockitoBean
	private InstalledContractService installedContractServiceMock;

	private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

	@BeforeEach
	void setUpTestData() {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilter(((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        })).build();

		ic1 = new InstalledContract();
		ic1.setId(1L);
		ic2 = new InstalledContract();
		ic2.setId(2L);
		dto1 = new InstalledContractDTO(
			1L,
			null,
			null
		);
		dto2 = new InstalledContractDTO(
			2L,
			null,
			null
		);
	}

	@Test
	void getAllAcceptJson_shouldReturnAll() throws Exception {

		when(installedContractServiceMock.findAll(any(InstalledContractCriteria.class))).thenReturn(Arrays.asList(ic1, ic2));

		mockMvc.perform(get("/api/v2/installedContracts").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")).andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(ic1.getId().intValue())))
			.andExpect(jsonPath("$.[1].id").value(is(ic2.getId().intValue())))
		;

		verify(installedContractServiceMock, times(1)).findAll(any(InstalledContractCriteria.class));
		verifyNoMoreInteractions(installedContractServiceMock);
	}

	@Test
	void getAllAcceptXml_shouldReturnAll() throws Exception {
		when(installedContractServiceMock.findAll(any(InstalledContractCriteria.class)))
				.thenReturn(Arrays.asList(ic1, ic2));

		mockMvc.perform(get("/api/v2/installedContracts").accept(MediaType.APPLICATION_XML))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
				.andExpect(xpath("/installedContracts/installedContract[1]/id").string("1"))
				.andExpect(xpath("/installedContracts/installedContract[2]/id").string("2"));

		verify(installedContractServiceMock).findAll(any(InstalledContractCriteria.class));
	}

	@Test
	void getAcceptJson_shouldReturnOne() throws Exception {
		when(installedContractServiceMock.find(ic1.getId())).thenReturn(ic1);

		mockMvc.perform(get("/api/v2/installedContracts/{id}", ic1.getId())
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
				.andExpect(jsonPath("$.id").value(ic1.getId().intValue()));

		verify(installedContractServiceMock).find(ic1.getId());
	}

	@Test
	void getAcceptXml_shouldReturnOne() throws Exception {
		when(installedContractServiceMock.find(ic1.getId())).thenReturn(ic1);

		mockMvc.perform(get("/api/v2/installedContracts/{id}", ic1.getId())
						.accept(MediaType.APPLICATION_XML))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
				.andExpect(xpath("/installedContract/id").string("1"));

		verify(installedContractServiceMock).find(ic1.getId());
	}

	@Test
	void get_shouldReturnNotFoundWhenContractDoesNotExist() throws Exception {
		when(installedContractServiceMock.find(anyLong())).thenReturn(null);

		mockMvc.perform(get("/api/v2/installedContracts/{id}", Long.MAX_VALUE)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertInstanceOf(ResourceNotFoundException.class,
						result.getResolvedException()));
	}
}
