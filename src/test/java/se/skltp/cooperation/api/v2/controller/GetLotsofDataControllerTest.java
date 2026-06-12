/**
 * Copyright (c) 2015-2026 Inera.
 * * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.v2.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.OutputStream;

class GetLotsofDataControllerTest {

	private static final int ONE_MB = 1024 * 1024;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new GetLotsofDataController()).build();
	}

	@Test
	void getLotsOfData_shouldReturnRequestedMegabytes() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/api/v2/lotsOfData").param("mb", "1").accept(MediaType.TEXT_PLAIN))
			.andExpect(request().asyncStarted())
			.andReturn();

		mockMvc.perform(asyncDispatch(mvcResult))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
			.andExpect(header().string("Content-Encoding", "identity"))
			.andExpect(result -> {
				byte[] response = result.getResponse().getContentAsByteArray();
				assertEquals(ONE_MB, response.length, "Expected response length to be exactly 1 MB");
			});
	}

	@Test
	void getLotsOfData_shouldRejectZeroMb() throws Exception {
		mockMvc.perform(get("/api/v2/lotsOfData").param("mb", "0"))
			.andExpect(status().isBadRequest());
	}

	@Test
	void getLotsOfData_shouldRejectOverMaxMb() throws Exception {
		mockMvc.perform(get("/api/v2/lotsOfData").param("mb", "102"))
			.andExpect(status().isBadRequest());
	}

	@Test
	void getLotsOfData_shouldAcceptAndStreamMax101Mb() throws Exception {
		ResponseEntity<StreamingResponseBody> response = new GetLotsofDataController().getLotsOfData(101);
		assertEquals(200, response.getStatusCode().value());

		final long[] countedBytes = new long[] { 0L };
		OutputStream countingStream = new OutputStream() {
			@Override
			public void write(int b) {
				countedBytes[0]++;
			}

			@Override
			public void write(@NonNull byte[] b, int off, int len) {
				countedBytes[0] += len;
			}
		};

		Assertions.assertNotNull(response.getBody());
		response.getBody().writeTo(countingStream);
		assertEquals(101L * ONE_MB, countedBytes[0], "Expected response length to be exactly 101 MB");
	}
}



