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

import org.dozer.DozerBeanMapper;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import se.skltp.cooperation.Application;
import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.service.ConnectionPointCriteria;
import se.skltp.cooperation.service.ConnectionPointService;
import se.skltp.cooperation.web.rest.exception.ResourceNotFoundException;
import se.skltp.cooperation.web.rest.v1.dto.ConnectionPointDTO;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
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
 * Test class for the ConnectionPointController REST controller.
 *
 * @author Peter Merikan
 * @see ConnectionPointController
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ConnectionPointControllerTest {

	private static DateTimeFormatter isoDateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ").withZone(DateTimeZone.forID("CET"));

	@InjectMocks
	ConnectionPointController uut;
	ConnectionPoint cp1;
	ConnectionPoint cp2;
	ConnectionPointDTO dto1;
	ConnectionPointDTO dto2;
	@Mock
	private ConnectionPointService connectionPointServiceMock;
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

		cp1 = new ConnectionPoint();
		cp1.setId(1L);
		cp2 = new ConnectionPoint();
		cp2.setId(2L);
		dto1 = new ConnectionPointDTO();
		dto1.setId(1L);
		dto1.setPlatform("dto1.platform");
		dto1.setEnvironment("dto1.environment");
		dto1.setSnapshotTime(isoDateFormatter.parseDateTime("2015-10-13T10:14:25+0200").toDate());
		dto2 = new ConnectionPointDTO();
		dto2.setId(2L);
		dto2.setPlatform("dto2.platform");
		dto2.setEnvironment("dto2.environment");
		dto2.setSnapshotTime(isoDateFormatter.parseDateTime("2015-10-13T10:15:12+0200").toDate());

	}

	@Test
	public void getAllAcceptJson_shouldReturnAll() throws Exception {

		when(connectionPointServiceMock.findAll(any(ConnectionPointCriteria.class))).thenReturn(Arrays.asList(cp1, cp2));
		when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);
		when(mapperMock.map(cp2, ConnectionPointDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/api/v1/connectionPoints").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(dto1.getId().intValue())))
			.andExpect(jsonPath("$.[0].platform").value(is(dto1.getPlatform())))
			.andExpect(jsonPath("$.[0].environment").value(is(dto1.getEnvironment())))
			.andExpect(jsonPath("$.[0].snapshotTime", is(isoDateFormatter.print(dto1.getSnapshotTime().getTime()))))
			.andExpect(jsonPath("$.[1].id").value(is(dto2.getId().intValue())))
			.andExpect(jsonPath("$.[1].platform").value(is(dto2.getPlatform())))
			.andExpect(jsonPath("$.[1].environment").value(is(dto2.getEnvironment())))
			.andExpect(jsonPath("$.[1].snapshotTime", is(isoDateFormatter.print(dto2.getSnapshotTime().getTime()))))
		;

		verify(connectionPointServiceMock, times(1)).findAll(any(ConnectionPointCriteria.class));
		verifyNoMoreInteractions(connectionPointServiceMock);

	}

	@Test
	public void getAllJsonUrl_shouldReturnAll() throws Exception {

		when(connectionPointServiceMock.findAll(any(ConnectionPointCriteria.class))).thenReturn(Arrays.asList(cp1, cp2));
		when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);
		when(mapperMock.map(cp2, ConnectionPointDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/api/v1/connectionPoints.json").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(dto1.getId().intValue())))
			.andExpect(jsonPath("$.[0].platform").value(is(dto1.getPlatform())))
			.andExpect(jsonPath("$.[0].environment").value(is(dto1.getEnvironment())))
			.andExpect(jsonPath("$.[0].snapshotTime", is(isoDateFormatter.print(dto1.getSnapshotTime().getTime()))))
			.andExpect(jsonPath("$.[1].id").value(is(dto2.getId().intValue())))
			.andExpect(jsonPath("$.[1].platform").value(is(dto2.getPlatform())))
			.andExpect(jsonPath("$.[1].environment").value(is(dto2.getEnvironment())))
			.andExpect(jsonPath("$.[1].snapshotTime", is(isoDateFormatter.print(dto2.getSnapshotTime().getTime()))))
		;

		verify(connectionPointServiceMock, times(1)).findAll(any(ConnectionPointCriteria.class));
		verifyNoMoreInteractions(connectionPointServiceMock);

	}

	@Test
	public void getAllAcceptXml_shouldReturnAll() throws Exception {

		when(connectionPointServiceMock.findAll(any(ConnectionPointCriteria.class))).thenReturn(Arrays.asList(cp1, cp2));
		when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);
		when(mapperMock.map(cp2, ConnectionPointDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/api/v1/connectionPoints").accept(MediaType.APPLICATION_XML)).andExpect(status().isOk())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/id").string(is(dto1.getId().toString())))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/platform").string(is(dto1.getPlatform())))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/environment").string(is(dto1.getEnvironment())))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/snapshotTime").string(is(isoDateFormatter.print(dto1.getSnapshotTime().getTime()))))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/id").string(is(dto2.getId().toString())))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/platform").string(is(dto2.getPlatform())))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/environment").string(is(dto2.getEnvironment())))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/snapshotTime").string(is(isoDateFormatter.print(dto2.getSnapshotTime().getTime()))));

		verify(connectionPointServiceMock, times(1)).findAll(any(ConnectionPointCriteria.class));
		verifyNoMoreInteractions(connectionPointServiceMock);

	}

	@Test
	public void getAllXmlUrl_shouldReturnAll() throws Exception {

		when(connectionPointServiceMock.findAll(any(ConnectionPointCriteria.class))).thenReturn(Arrays.asList(cp1, cp2));
		when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);
		when(mapperMock.map(cp2, ConnectionPointDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/api/v1/connectionPoints.xml").accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/id").string(is(dto1.getId().toString())))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/platform").string(is(dto1.getPlatform())))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/environment").string(is(dto1.getEnvironment())))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/snapshotTime").string(is(isoDateFormatter.print(dto1.getSnapshotTime().getTime()))))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/id").string(is(dto2.getId().toString())))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/platform").string(is(dto2.getPlatform())))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/environment").string(is(dto2.getEnvironment())))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/snapshotTime").string(is(isoDateFormatter.print(dto2.getSnapshotTime().getTime()))));

		verify(connectionPointServiceMock, times(1)).findAll(any(ConnectionPointCriteria.class));
		verifyNoMoreInteractions(connectionPointServiceMock);

	}

	@Test
	public void getAllAcceptJson_shouldReturnEmptyList() throws Exception {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null, null, null, null, null, null);
		when(connectionPointServiceMock.findAll(criteria)).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/api/v1/connectionPoints").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void getAllAcceptXml_shouldReturnEmptyList() throws Exception {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null, null, null, null, null, null);
		when(connectionPointServiceMock.findAll(criteria)).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/api/v1/connectionPoints").accept(MediaType.APPLICATION_XML)).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML))
			.andExpect(xpath("/connectionPoints").nodeCount(1))
			.andExpect(xpath("/connectionPoints/*").nodeCount(0));

	}

	@Test
	public void getAccept_shouldReturnOneAsJson() throws Exception {

		when(connectionPointServiceMock.find(cp1.getId())).thenReturn(cp1);
		when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);

		mockMvc.perform(get("/api/v1/connectionPoints/{id}", cp1.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$.id").value(dto1.getId().intValue()))
			.andExpect(jsonPath("$.platform").value(dto1.getPlatform()))
			.andExpect(jsonPath("$.environment").value(dto1.getEnvironment()))
			.andExpect(jsonPath("$.snapshotTime", is(isoDateFormatter.print(dto1.getSnapshotTime().getTime()))));
	}

	@Test
	public void getAccept_shouldReturnOneAsXml() throws Exception {

		when(connectionPointServiceMock.find(cp1.getId())).thenReturn(cp1);
		when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);

		mockMvc.perform(get("/api/v1/connectionPoints/{id}", cp1.getId()).accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/connectionPoint/id").string(is(dto1.getId().toString())))
			.andExpect(xpath("/connectionPoint/platform").string(is(dto1.getPlatform())))
			.andExpect(xpath("/connectionPoint/environment").string(is(dto1.getEnvironment())))
			.andExpect(xpath("/connectionPoint/snapshotTime").string(is(isoDateFormatter.print(dto1.getSnapshotTime().getTime()))));
	}

	@Test
	public void getJsonUrl_shouldReturnOneAsJson() throws Exception {

		when(connectionPointServiceMock.find(cp1.getId())).thenReturn(cp1);
		when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);

		mockMvc.perform(get("/api/v1/connectionPoints.json/{id}", cp1.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$.id").value(dto1.getId().intValue()))
			.andExpect(jsonPath("$.platform").value(dto1.getPlatform()))
			.andExpect(jsonPath("$.environment").value(dto1.getEnvironment()))
			.andExpect(jsonPath("$.snapshotTime", is(isoDateFormatter.print(dto1.getSnapshotTime().getTime()))));
	}

	@Test
	public void getXmlUrl_shouldReturnOneAsXml() throws Exception {

		when(connectionPointServiceMock.find(cp1.getId())).thenReturn(cp1);
		when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);

		mockMvc.perform(get("/api/v1/connectionPoints.xml/{id}", cp1.getId()).accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/connectionPoint/id").string(is(dto1.getId().toString())))
			.andExpect(xpath("/connectionPoint/platform").string(is(dto1.getPlatform())))
			.andExpect(xpath("/connectionPoint/environment").string(is(dto1.getEnvironment())))
			.andExpect(xpath("/connectionPoint/snapshotTime").string(is(isoDateFormatter.print(dto1.getSnapshotTime().getTime()))));
	}

	@Test
	public void getXmlUrl2_shouldReturnOneAsXml() throws Exception {

		when(connectionPointServiceMock.find(cp1.getId())).thenReturn(cp1);
		when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);

		mockMvc.perform(get("/api/v1/connectionPoints/{id}.xml", cp1.getId()).accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/connectionPoint/id").string(is(dto1.getId().toString())))
			.andExpect(xpath("/connectionPoint/platform").string(is(dto1.getPlatform())))
			.andExpect(xpath("/connectionPoint/environment").string(is(dto1.getEnvironment())))
			.andExpect(xpath("/connectionPoint/snapshotTime").string(is(isoDateFormatter.print(dto1.getSnapshotTime().getTime()))));
	}

	@Test
	public void get_shouldThrowNotFoundException() throws Exception {

		when(connectionPointServiceMock.find(anyLong())).thenReturn(null);
		try {
			mockMvc.perform(get("/api/v1/connectionPoints/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
			fail("Should thrown a exception");
		} catch (Exception e) {
			assertEquals(e.getCause().getClass(), ResourceNotFoundException.class);
		}
	}

}
