/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.basicauthmodule;

import tools.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyAuthEntryPoint implements AuthenticationEntryPoint {

	/**
	 * Implements backwards-compatible error info for auth failures
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		Map<String, Object> error = new LinkedHashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		error.put("timestamp", new Date().getTime());
		error.put("status", status.value());
		error.put("error", status.getReasonPhrase());
		error.put("path", request.getRequestURI());

		ObjectMapper mapper = new ObjectMapper();
		String jsonError = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);

		response.setStatus(status.value());
		response.setContentType("application/json");
		response.getWriter().write(jsonError);
	}

}
