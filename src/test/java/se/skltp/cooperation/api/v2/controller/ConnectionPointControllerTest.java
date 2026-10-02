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

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.service.ConnectionPointCriteria;
import se.skltp.cooperation.service.ConnectionPointService;
import se.skltp.cooperation.api.exception.ResourceNotFoundException;

/**
 * Test class for the ConnectionPointController REST controller.
 *
 * @see ConnectionPointController
 */

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@WebAppConfiguration
class ConnectionPointControllerTest {

	private static final DateTimeFormatter ISO_DATE_FORMATTER =
		DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
			.withZone(ZoneId.of("CET"));

	private static Date parseDateFromText(String value) {
		return Date.from(ZonedDateTime.parse(value, ISO_DATE_FORMATTER).toInstant());
	}
	private static String convertDateToText(Date value) {
		return ISO_DATE_FORMATTER.format(Instant.ofEpochMilli(value.getTime()));
	}

	ConnectionPoint conn1;
	ConnectionPoint conn2;

	@MockitoBean
	private ConnectionPointService connectionPointServiceMock;

	@Autowired
	private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

	@BeforeEach
	void setUpTestData() {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
			.addFilter(((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        })).build();

		conn1 = new ConnectionPoint();
		conn1.setId(1L);
		conn1.setPlatform("SWEDEN");
		conn1.setEnvironment("PRODUCTION");
		conn1.setSnapshotTime(parseDateFromText("2026-10-01T00:05:03+0200"));

		conn2 = new ConnectionPoint();
		conn2.setId(2L);
		conn2.setPlatform("NORWAY");
		conn2.setEnvironment("TEST");
		conn2.setSnapshotTime(parseDateFromText("2026-10-01T00:06:03+0200"));
	}

	@Test
	void getAllAcceptJson_shouldReturnAll() throws Exception {

		when(connectionPointServiceMock.findAll(any(ConnectionPointCriteria.class)))
			.thenReturn(Arrays.asList(conn1, conn2));

		mockMvc.perform(get("/api/v2/connectionPoints")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(conn1.getId().intValue())))
			.andExpect(jsonPath("$.[0].platform").value(is(conn1.getPlatform())))
			.andExpect(jsonPath("$.[0].environment").value(is(conn1.getEnvironment())))
			.andExpect(jsonPath("$.[0].snapshotTime", is(convertDateToText(conn1.getSnapshotTime()))))
			.andExpect(jsonPath("$.[1].id").value(is(conn2.getId().intValue())))
			.andExpect(jsonPath("$.[1].platform").value(is(conn2.getPlatform())))
			.andExpect(jsonPath("$.[1].environment").value(is(conn2.getEnvironment())))
			.andExpect(jsonPath("$.[1].snapshotTime", is(convertDateToText(conn2.getSnapshotTime()))))
		;

		verify(connectionPointServiceMock, times(1))
			.findAll(any(ConnectionPointCriteria.class));
		verifyNoMoreInteractions(connectionPointServiceMock);
	}

	@Test
	void getAllJsonUrl_shouldReturnAll() throws Exception {

		when(connectionPointServiceMock.findAll(any(ConnectionPointCriteria.class)))
			.thenReturn(Arrays.asList(conn1, conn2));

		mockMvc.perform(get("/api/v2/connectionPoints.json")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").value(is(conn1.getId().intValue())))
			.andExpect(jsonPath("$.[0].platform").value(is(conn1.getPlatform())))
			.andExpect(jsonPath("$.[0].environment").value(is(conn1.getEnvironment())))
			.andExpect(jsonPath("$.[0].snapshotTime", is(convertDateToText(conn1.getSnapshotTime()))))
			.andExpect(jsonPath("$.[1].id").value(is(conn2.getId().intValue())))
			.andExpect(jsonPath("$.[1].platform").value(is(conn2.getPlatform())))
			.andExpect(jsonPath("$.[1].environment").value(is(conn2.getEnvironment())))
			.andExpect(jsonPath("$.[1].snapshotTime", is(convertDateToText(conn2.getSnapshotTime()))))
		;

		verify(connectionPointServiceMock, times(1)).findAll(any(ConnectionPointCriteria.class));
		verifyNoMoreInteractions(connectionPointServiceMock);
	}

	@Test
	void getAllAcceptXml_shouldReturnAll() throws Exception {

		when(connectionPointServiceMock.findAll(any(ConnectionPointCriteria.class)))
			.thenReturn(Arrays.asList(conn1, conn2));

		mockMvc.perform(get("/api/v2/connectionPoints").accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/id").string(is(conn1.getId().toString())))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/platform").string(is(conn1.getPlatform())))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/environment").string(is(conn1.getEnvironment())))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/snapshotTime").string(is(convertDateToText(conn1.getSnapshotTime()))))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/id").string(is(conn2.getId().toString())))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/platform").string(is(conn2.getPlatform())))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/environment").string(is(conn2.getEnvironment())))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/snapshotTime").string(is(convertDateToText(conn2.getSnapshotTime()))));

		verify(connectionPointServiceMock, times(1)).findAll(any(ConnectionPointCriteria.class));
		verifyNoMoreInteractions(connectionPointServiceMock);
	}

	@Test
	void getAllXmlUrl_shouldReturnAll() throws Exception {

		when(connectionPointServiceMock.findAll(any(ConnectionPointCriteria.class)))
			.thenReturn(Arrays.asList(conn1, conn2));

		mockMvc.perform(get("/api/v2/connectionPoints.xml")
				.accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/id").string(is(conn1.getId().toString())))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/platform").string(is(conn1.getPlatform())))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/environment").string(is(conn1.getEnvironment())))
			.andExpect(xpath("/connectionPoints/connectionPoint[1]/snapshotTime").string(is(convertDateToText(conn1.getSnapshotTime()))))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/id").string(is(conn2.getId().toString())))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/platform").string(is(conn2.getPlatform())))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/environment").string(is(conn2.getEnvironment())))
			.andExpect(xpath("/connectionPoints/connectionPoint[2]/snapshotTime").string(is(convertDateToText(conn2.getSnapshotTime()))));

		verify(connectionPointServiceMock, times(1)).findAll(any(ConnectionPointCriteria.class));
		verifyNoMoreInteractions(connectionPointServiceMock);
	}

	@Test
	void getAllAcceptJson_shouldReturnEmptyList() throws Exception {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null,
			null,
			null,
			null,
			null,
			null);
		when(connectionPointServiceMock.findAll(criteria)).thenReturn(new ArrayList<>());

		mockMvc.perform(get("/api/v2/connectionPoints").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(content().encoding("UTF-8"))
			.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	void getAllAcceptXml_shouldReturnEmptyList() throws Exception {

		ConnectionPointCriteria criteria = new ConnectionPointCriteria(null,
			null,
			null,
			null,
			null,
			null);
		when(connectionPointServiceMock.findAll(criteria)).thenReturn(new ArrayList<>());

		mockMvc.perform(get("/api/v2/connectionPoints").accept(MediaType.APPLICATION_XML_VALUE))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(content().encoding("UTF-8"))
			.andExpect(xpath("/connectionPoints").nodeCount(1))
			.andExpect(xpath("/connectionPoints/*").nodeCount(0));
	}

	@Test
	void getAccept_shouldReturnOneAsJson() throws Exception {

		when(connectionPointServiceMock.find(conn1.getId())).thenReturn(conn1);

		mockMvc.perform(get("/api/v2/connectionPoints/{id}", conn1.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
			.andExpect(content().encoding("UTF-8"))
			.andExpect(jsonPath("$.id").value(conn1.getId().intValue()))
			.andExpect(jsonPath("$.platform").value(conn1.getPlatform()))
			.andExpect(jsonPath("$.environment").value(conn1.getEnvironment()))
			.andExpect(jsonPath("$.snapshotTime", is(convertDateToText(conn1.getSnapshotTime()))));
	}

	@Test
	void getAccept_shouldReturnOneAsXml() throws Exception {

		when(connectionPointServiceMock.find(conn1.getId())).thenReturn(conn1);

		mockMvc.perform(get("/api/v2/connectionPoints/{id}", conn1.getId()).accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(content().encoding("UTF-8"))
			.andExpect(xpath("/connectionPoint/id").string(is(conn1.getId().toString())))
			.andExpect(xpath("/connectionPoint/platform").string(is(conn1.getPlatform())))
			.andExpect(xpath("/connectionPoint/environment").string(is(conn1.getEnvironment())))
			.andExpect(xpath("/connectionPoint/snapshotTime").string(is(convertDateToText(conn1.getSnapshotTime()))));
	}

	@Test
	void getJsonUrl_shouldReturnOneAsJson() throws Exception {

		when(connectionPointServiceMock.find(conn1.getId())).thenReturn(conn1);

		mockMvc.perform(get("/api/v2/connectionPoints.json/{id}", conn1.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(content().encoding("UTF-8"))
			.andExpect(jsonPath("$.id").value(conn1.getId().intValue()))
			.andExpect(jsonPath("$.platform").value(conn1.getPlatform()))
			.andExpect(jsonPath("$.environment").value(conn1.getEnvironment()))
			.andExpect(jsonPath("$.snapshotTime", is(convertDateToText(conn1.getSnapshotTime()))));
	}

	@Test
	void getXmlUrl_shouldReturnOneAsXml() throws Exception {

		when(connectionPointServiceMock.find(conn1.getId())).thenReturn(conn1);

		mockMvc.perform(get("/api/v2/connectionPoints.xml/{id}", conn1.getId()).accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(content().encoding("UTF-8"))
			.andExpect(xpath("/connectionPoint/id").string(is(conn1.getId().toString())))
			.andExpect(xpath("/connectionPoint/platform").string(is(conn1.getPlatform())))
			.andExpect(xpath("/connectionPoint/environment").string(is(conn1.getEnvironment())))
			.andExpect(xpath("/connectionPoint/snapshotTime").string(is(convertDateToText(conn1.getSnapshotTime()))));
	}

	@Test
	void getXmlUrl2_shouldReturnOneAsXml() throws Exception {

		when(connectionPointServiceMock.find(conn1.getId())).thenReturn(conn1);

		mockMvc.perform(get("/api/v2/connectionPoints/{id}.xml", conn1.getId()).accept(MediaType.APPLICATION_XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_XML + ";charset=UTF-8"))
			.andExpect(xpath("/connectionPoint/id").string(is(conn1.getId().toString())))
			.andExpect(xpath("/connectionPoint/platform").string(is(conn1.getPlatform())))
			.andExpect(xpath("/connectionPoint/environment").string(is(conn1.getEnvironment())))
			.andExpect(xpath("/connectionPoint/snapshotTime").string(is(convertDateToText(conn1.getSnapshotTime()))));
	}

	@Test
	void get_shouldThrowNotFoundException() throws Exception {

		when(connectionPointServiceMock.find(anyLong())).thenReturn(null);

		mockMvc.perform(get("/api/v2/connectionPoints/{id}", Long.MAX_VALUE)
	    	      .contentType(MediaType.APPLICATION_JSON))
	    	      .andExpect(status().isNotFound())
	    	      .andExpect(result -> assertInstanceOf(ResourceNotFoundException.class, result.getResolvedException())
	    	      );
	}
}
