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

	/**
	 * Plain Bearer tokens are rejected by {@link RejectBearerTokenFilter}
	 * before the JWT signature is even checked.
	 */
	@Test
	void bearerTokenIsRejectedWith401() throws Exception {
		mockMvc.perform(get("/api/v2/cooperations")
				.header("Authorization", "Bearer any.plain.bearer.token"))
			.andExpect(status().isUnauthorized());
	}

	/**
	 * A DPoP-scheme authorization header without a {@code DPoP} proof header is rejected
	 * by Spring Security's built-in {@code DPoPAuthenticationConfigurer} even if the
	 * access token would otherwise be valid.
	 */
	@Test
	void dpopTokenWithoutProofHeaderIsRejected() throws Exception {
		mockMvc.perform(get("/api/v2/cooperations")
				.header("Authorization", "DPoP any.dpop.token"))
			.andExpect(status().isUnauthorized());
	}
}
