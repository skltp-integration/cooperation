package se.skltp.cooperation.basicauthmodule;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationAuthenticationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void docsDoNotRequireAuthentication() throws Exception {
		mockMvc.perform(get("/doc/index_v2.html")).andExpect(status().isOk());
		mockMvc.perform(get("/doc/openapi_v2.json")).andExpect(status().isOk());
	}

	@Test
	void apisDoRequireAuthentication() throws Exception {
		mockMvc.perform(get("/api/logicalAddresses"))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.status", is(401)))
			.andExpect(jsonPath("$.error", is("Unauthorized")))
			.andExpect(jsonPath("$.path", is("/api/logicalAddresses")))
			.andExpect(jsonPath("$.timestamp", anything()));
	}
}
