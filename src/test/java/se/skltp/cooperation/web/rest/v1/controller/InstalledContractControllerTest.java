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
package se.skltp.cooperation.web.rest.v1.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import se.skltp.cooperation.Application;
import se.skltp.cooperation.domain.InstalledContract;
import se.skltp.cooperation.service.InstalledContractCriteria;
import se.skltp.cooperation.service.InstalledContractService;
import se.skltp.cooperation.web.rest.v1.dto.InstalledContractDTO;

/**
 * Test class for the ConnectionPointController REST controller.
 *
 * @author Jan Vasternas
 * @see InstalledContractController
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class InstalledContractControllerTest {


	InstalledContract ic1;
	InstalledContract ic2;
	InstalledContractDTO dto1;
	InstalledContractDTO dto2;
	@MockBean
	private InstalledContractService installedContractServiceMock;
	@MockBean
	private DozerBeanMapper mapperMock;
	private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

	@Before
	public void setUpTestData() throws Exception {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilter(((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        })).build();

		ic1 = new InstalledContract();
		ic1.setId(1L);
		ic2 = new InstalledContract();
		ic2.setId(2L);
		dto1 = new InstalledContractDTO();
		dto1.setId(1L);
		dto2 = new InstalledContractDTO();
		dto2.setId(2L);
	}

	@Test
	public void getAllAcceptJson_shouldReturnAll() throws Exception {

		when(installedContractServiceMock.findAll(any(InstalledContractCriteria.class))).thenReturn(Arrays.asList(ic1, ic2));
		when(mapperMock.map(ic1, InstalledContractDTO.class)).thenReturn(dto1);
		when(mapperMock.map(ic2, InstalledContractDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/api/v1/installedContracts").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")).andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(dto1.getId().intValue())))
			.andExpect(jsonPath("$.[1].id").value(is(dto2.getId().intValue())))
		;

		verify(installedContractServiceMock, times(1)).findAll(any(InstalledContractCriteria.class));
		verifyNoMoreInteractions(installedContractServiceMock);

	}

}
