package se.skltp.cooperation.basicauthmodule;

import com.fasterxml.jackson.databind.ObjectMapper;
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
