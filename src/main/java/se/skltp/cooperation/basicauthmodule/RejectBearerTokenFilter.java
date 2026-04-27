package se.skltp.cooperation.basicauthmodule;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Rejects any request that carries a plain {@code Authorization: Bearer} token.
 *
 * <p>This resource server only accepts DPoP-bound tokens ({@code Authorization: DPoP}).
 * Plain Bearer tokens are sender-unconstrained and must be refused per security policy.
 * Requests using {@code Authorization: Basic} (or no Authorization header) are not
 * affected and pass through to the next filter.</p>
 *
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc9449">RFC 9449 — OAuth 2.0
 * Demonstrating Proof of Possession (DPoP)</a>
 */
public class RejectBearerTokenFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain) throws ServletException, IOException {

		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(authorization)
			&& StringUtils.startsWithIgnoreCase(authorization, "bearer ")) {

			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setHeader(HttpHeaders.WWW_AUTHENTICATE,
				"DPoP error=\"invalid_token\", " +
					"error_description=\"Plain Bearer tokens are not accepted; use DPoP (RFC 9449)\"");
			response.setContentType("application/json");
			response.getWriter().write(
				"{\"status\":401,\"error\":\"Unauthorized\"," +
					"\"message\":\"Plain Bearer tokens are not accepted by this resource server; use DPoP (RFC 9449)\"}");
			return;
		}

		chain.doFilter(request, response);
	}
}

