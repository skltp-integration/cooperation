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

import java.util.ArrayList;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
//import org.joda.time.DateTimeZone;
//import org.joda.time.format.DateTimeFormat;
//import org.joda.time.format.DateTimeFormatter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import se.skltp.cooperation.Application;
import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.service.ConnectionPointCriteria;
import se.skltp.cooperation.service.ConnectionPointService;
import se.skltp.cooperation.api.exception.ResourceNotFoundException;
import se.skltp.cooperation.api.v2.dto.ConnectionPointDTO;

/**
 * Test class for the ConnectionPointController REST controller.
 *
 * @see ConnectionPointController
 */

@SpringBootTest(classes = Application.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class ConnectionPointControllerTest {

	private static final DateTimeFormatter ISO_DATE_FORMATTER =
		DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
			.withZone(ZoneId.of("CET"));

	private static Date parseDate(String value) {
		return Date.from(ZonedDateTime.parse(value, ISO_DATE_FORMATTER).toInstant());
	}
	private static String formatDate(Date value) {
		return ISO_DATE_FORMATTER.format(Instant.ofEpochMilli(value.getTime()));
	}

	ConnectionPoint cp1;
	ConnectionPoint cp2;
	ConnectionPointDTO dto1;
	ConnectionPointDTO dto2;

	@MockitoBean
	private ConnectionPointService connectionPointServiceMock;

	@MockitoBean
	private ModelMapper mapperMock;

	@Autowired
	private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

	@BeforeEach
	public void setUpTestData() {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).addFilter(((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        })).build();

		cp1 = new ConnectionPoint();
		cp1.setId(1L);
		cp2 = new ConnectionPoint();
		cp2.setId(2L);
		dto1 = new ConnectionPointDTO();
		dto1.setId(1L);
		dto1.setPlatform("dto1.platform");
		dto1.setEnvironment("dto1.environment");
		dto1.setSnapshotTime(parseDate("2015-10-13T10:14:25+0200"));
		dto2 = new ConnectionPointDTO();
		dto2.setId(2L);
		dto2.setPlatform("dto2.platform");
		dto2.setEnvironment("dto2.environment");
		dto2.setSnapshotTime(parseDate("2015-10-13T10:15:12+0200"));

	}

	@Test
	public void getAllAcceptJson_shouldReturnAll() throws Exception {

		when(connectionPointServiceMock.findAll(any(ConnectionPointCriteria.class))).thenReturn(Arrays.asList(cp1, cp2));
		when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);
		when(mapperMock.map(cp2, ConnectionPointDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/api/v2/connectionPoints").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")).andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(dto1.getId().intValue())))
			.andExpect(jsonPath("$.[0].platform").value(is(dto1.getPlatform())))
			.andExpect(jsonPath("$.[0].environment").value(is(dto1.getEnvironment())))
			.andExpect(jsonPath("$.[0].snapshotTime", is(formatDate(dto1.getSnapshotTime()))))
			.andExpect(jsonPath("$.[1].id").value(is(dto2.getId().intValue())))
			.andExpect(jsonPath("$.[1].platform").value(is(dto2.getPlatform())))
			.andExpect(jsonPath("$.[1].environment").value(is(dto2.getEnvironment())))
			.andExpect(jsonPath("$.[1].snapshotTime", is(formatDate(dto2.getSnapshotTime()))))
		;

		verify(connectionPointServiceMock, times(1)).findAll(any(ConnectionPointCriteria.class));
		verifyNoMoreInteractions(connectionPointServiceMock);

	}

	@Test
	public void getAllJsonUrl_shouldReturnAll() throws Exception {

		when(connectionPointServiceMock.findAll(any(ConnectionPointCriteria.class))).thenReturn(Arrays.asList(cp1, cp2));
		when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);
		when(mapperMock.map(cp2, ConnectionPointDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/api/v2/connectionPoints.json").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")).andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(dto1.getId().intValue())))
			.andExpect(jsonPath("$.[0].platform").value(is(dto1.getPlatform())))
			.andExpect(jsonPath("$.[0].environment").value(is(dto1.getEnvironment())))
			.andExpect(jsonPath("$.[0].snapshotTime", is(formatDate(dto1.getSnapshotTime()))))
			.andExpect(jsonPath("$.[1].id").value(is(dto2.getId().intValue())))
			.andExpect(jsonPath("$.[1].platform").value(is(dto2.getPlatform())))
			.andExpect(jsonPath("$.[1].environment").value(is(dto2.getEnvironment())))
			.andExpect(jsonPath("$.[1].snapshotTime", is(formatDate(dto2.getSnapshotTime()))))
		;

		verify(connectionPointServiceMock, times(1)).findAll(any(ConnectionPointCriteria.class));
		verifyNoMoreInteractions(connectionPointServiceMock);

	}

	@Test
	public void getAllAcceptXml_shouldReturnAll() throws Exception {

		when(connectionPointServiceMock.findAll(any(ConnectionPointCriteria.class))).thenReturn(Arrays.asList(cp1, cp2));
		when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);
		when(mapperMock.map(cp2, ConnectionPointDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/api/v2/connectionPoints").accept(MediaType.APPLICATION_XML)).andExpect(status().isOk())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/id").string(is(dto1.getId().toString())))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/platform").string(is(dto1.getPlatform())))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/environment").string(is(dto1.getEnvironment())))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/snapshotTime").string(is(formatDate(dto1.getSnapshotTime()))))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/id").string(is(dto2.getId().toString())))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/platform").string(is(dto2.getPlatform())))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/environment").string(is(dto2.getEnvironment())))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/snapshotTime").string(is(formatDate(dto2.getSnapshotTime()))));

		verify(connectionPointServiceMock, times(1)).findAll(any(ConnectionPointCriteria.class));
		verifyNoMoreInteractions(connectionPointServiceMock);

	}

	@Test
	public void getAllXmlUrl_shouldReturnAll() throws Exception {

		when(connectionPointServiceMock.findAll(any(ConnectionPointCriteria.class))).thenReturn(Arrays.asList(cp1, cp2));
		when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);
		when(mapperMock.map(cp2, ConnectionPointDTO.class)).thenReturn(dto2);

		mockMvc.perform(get("/api/v2/connectionPoints.xml").accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/id").string(is(dto1.getId().toString())))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/platform").string(is(dto1.getPlatform())))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/environment").string(is(dto1.getEnvironment())))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/snapshotTime").string(is(formatDate(dto1.getSnapshotTime()))))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/id").string(is(dto2.getId().toString())))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/platform").string(is(dto2.getPlatform())))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/environment").string(is(dto2.getEnvironment())))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/snapshotTime").string(is(formatDate(dto2.getSnapshotTime()))));

		verify(connectionPointServiceMock, times(1)).findAll(any(ConnectionPointCriteria.class));
		verifyNoMoreInteractions(connectionPointServiceMock);

	}

	@Test
	public void getAllAcceptJson_shouldReturnEmptyList() throws Exception {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null, null, null, null, null, null);
		when(connectionPointServiceMock.findAll(criteria)).thenReturn(new ArrayList<>());

		mockMvc.perform(get("/api/v2/connectionPoints").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(content().encoding("UTF-8"))
			.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void getAllAcceptXml_shouldReturnEmptyList() throws Exception {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null, null, null, null, null, null);
		when(connectionPointServiceMock.findAll(criteria)).thenReturn(new ArrayList<>());

		mockMvc.perform(get("/api/v2/connectionPoints").accept(MediaType.APPLICATION_XML_VALUE))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(content().encoding("UTF-8"))
			.andExpect(xpath("/connectionPoints").nodeCount(1))
			.andExpect(xpath("/connectionPoints/*").nodeCount(0));

	}

	@Test
	public void getAccept_shouldReturnOneAsJson() throws Exception {

		when(connectionPointServiceMock.find(anyLong())).thenReturn(cp1);
		when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);

		mockMvc.perform(get("/api/v2/connectionPoints/{id}", cp1.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
			.andExpect(content().encoding("UTF-8"))
			.andExpect(jsonPath("$.id").value(dto1.getId().intValue()))
			.andExpect(jsonPath("$.platform").value(dto1.getPlatform()))
			.andExpect(jsonPath("$.environment").value(dto1.getEnvironment()))
			.andExpect(jsonPath("$.snapshotTime", is(formatDate(dto1.getSnapshotTime()))));
	}

	@Test
	public void getAccept_shouldReturnOneAsXml() throws Exception {

		when(connectionPointServiceMock.find(cp1.getId())).thenReturn(cp1);
		when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);

		mockMvc.perform(get("/api/v2/connectionPoints/{id}", cp1.getId()).accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(content().encoding("UTF-8"))
			.andExpect(xpath("/connectionPoint/id").string(is(dto1.getId().toString())))
			.andExpect(xpath("/connectionPoint/platform").string(is(dto1.getPlatform())))
			.andExpect(xpath("/connectionPoint/environment").string(is(dto1.getEnvironment())))
			.andExpect(xpath("/connectionPoint/snapshotTime").string(is(formatDate(dto1.getSnapshotTime()))));
	}

	@Test
	public void getJsonUrl_shouldReturnOneAsJson() throws Exception {

		when(connectionPointServiceMock.find(cp1.getId())).thenReturn(cp1);
		when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);

		mockMvc.perform(get("/api/v2/connectionPoints.json/{id}", cp1.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(content().encoding("UTF-8"))
			.andExpect(jsonPath("$.id").value(dto1.getId().intValue()))
			.andExpect(jsonPath("$.platform").value(dto1.getPlatform()))
			.andExpect(jsonPath("$.environment").value(dto1.getEnvironment()))
			.andExpect(jsonPath("$.snapshotTime", is(formatDate(dto1.getSnapshotTime()))));
	}

	@Test
	public void getXmlUrl_shouldReturnOneAsXml() throws Exception {

		when(connectionPointServiceMock.find(cp1.getId())).thenReturn(cp1);
		when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);

		mockMvc.perform(get("/api/v2/connectionPoints.xml/{id}", cp1.getId()).accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(content().encoding("UTF-8"))
			.andExpect(xpath("/connectionPoint/id").string(is(dto1.getId().toString())))
			.andExpect(xpath("/connectionPoint/platform").string(is(dto1.getPlatform())))
			.andExpect(xpath("/connectionPoint/environment").string(is(dto1.getEnvironment())))
			.andExpect(xpath("/connectionPoint/snapshotTime").string(is(formatDate(dto1.getSnapshotTime()))));
	}

	@Test
	public void getXmlUrl2_shouldReturnOneAsXml() throws Exception {

		when(connectionPointServiceMock.find(cp1.getId())).thenReturn(cp1);
		when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);

		mockMvc.perform(get("/api/v2/connectionPoints/{id}.xml", cp1.getId()).accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/connectionPoint/id").string(is(dto1.getId().toString())))
			.andExpect(xpath("/connectionPoint/platform").string(is(dto1.getPlatform())))
			.andExpect(xpath("/connectionPoint/environment").string(is(dto1.getEnvironment())))
			.andExpect(xpath("/connectionPoint/snapshotTime").string(is(formatDate(dto1.getSnapshotTime()))));
	}

	@Test
	public void get_shouldThrowNotFoundException() throws Exception {

		when(connectionPointServiceMock.find(anyLong())).thenReturn(null);
		mockMvc.perform(get("/api/v2/connectionPoints/{id}", Long.MAX_VALUE)
	    	      .contentType(MediaType.APPLICATION_JSON))
	    	      .andExpect(status().isNotFound())
	    	      .andExpect(result -> assertInstanceOf(ResourceNotFoundException.class, result.getResolvedException())
	    	      );

	}

}
