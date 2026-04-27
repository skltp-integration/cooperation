//////
// 2022-05-17, Henrik Augustsson.
// Nordic Medtest.
//////

package se.skltp.cooperation.basicauthmodule;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@Configuration
@EnableWebSecurity
public class BasicAuthConfig {

	/** Scope value that the Keycloak coop realm grants for read access. */
	private static final String COOP_READ_SCOPE = "https://dev.ntjp.se/coop/read";

	@Bean
	public UserDetailsService userDetailsService() {
		return new MyUserDetailsService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(
		HttpSecurity http,
		@Value("${spring.h2.console.enabled:false}") boolean h2Console) throws Exception {
		http

			// !!! REGARDING CSRF PROTECTION !!!
			// We choose to disable CSRF protection here below.
			//   Our motivation is that CSRF attack protection is mostly relevant when a browser is involved,
			//   where an attacker page can exploit a user's pre-existing session cookie or some other stored token.
			// For stateless APIs, that communicate backend-backend; or where requests are never made from a browser,
			//   but only via API tools like Postman or Insomnia; CSRF protection is less relevant.
			.csrf(AbstractHttpConfigurer::disable)

			.authorizeHttpRequests(authz -> {
				if (h2Console) { // Managing the H2 database in dev profile
					authz.requestMatchers("/h2-console/**").permitAll();
				}
				authz
					// For the two versions of the primary API.
					.requestMatchers(HttpMethod.GET, "/api/v2/**").hasAnyAuthority(Settings.REG_USER_ROLE, Settings.REG_ADMIN_ROLE, Settings.AUTH_ADMIN_ROLE)
					.requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()

					// The swagger Docs are open outward.
					.requestMatchers(HttpMethod.GET, "/doc/**").permitAll()

					// Actuator endpoints does not require authentication (should only be reachable internally)
					.requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()

					// Ping always responds openly.
					.requestMatchers(HttpMethod.GET, "/authoring/ping").permitAll()

					// Roles assumed to inherit hierarchically.
					.requestMatchers(HttpMethod.GET, "/authoring/admin/**").hasAnyAuthority(Settings.AUTH_ADMIN_ROLE)
					.requestMatchers(HttpMethod.POST, "/authoring/admin/**").hasAnyAuthority(Settings.AUTH_ADMIN_ROLE)

					// Default level for all other endpoints is assumed restricted, as fallback.
					.requestMatchers(HttpMethod.GET, "/**").hasAnyAuthority(Settings.REG_USER_ROLE, Settings.REG_ADMIN_ROLE, Settings.AUTH_ADMIN_ROLE)
					.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Options always open tho'.

					.anyRequest().fullyAuthenticated();
			})

			// Basic auth — existing mechanism
			.httpBasic(Customizer.withDefaults())

			// JWT + DPoP — Spring Security 6.5 auto-configures DPoP proof validation
			// (DPoPAuthenticationConfigurer) when .jwt() is present and DPoPProofJwtDecoderFactory
			// is on the classpath.  This handles: typ, alg, jwk, signature, htm, htu, iat, ath,
			// cnf.jkt binding, and jti replay prevention — all per RFC 9449.
			.oauth2ResourceServer(oauth2 -> oauth2
				.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
			)

			// Reject plain "Authorization: Bearer" tokens early — only DPoP tokens are accepted.
			// This filter runs before the OAuth2 resource server filter so that Bearer tokens
			// never reach Spring's JWT decoder.
			.addFilterBefore(new RejectBearerTokenFilter(), UsernamePasswordAuthenticationFilter.class)

			.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(authenticationEntryPoint()));
		return http.build();
	}

	/**
	 * Maps JWT scopes to Spring Security authorities.
	 * The {@code https://dev.ntjp.se/coop/read} scope is mapped to the
	 * {@link Settings#REG_USER_ROLE USER} authority so the existing
	 * authorization rules on {@code /api/v2/**} are satisfied.
	 */
	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter scopeConverter = new JwtGrantedAuthoritiesConverter();
		scopeConverter.setAuthoritiesClaimName("scope");
		scopeConverter.setAuthorityPrefix("SCOPE_");

		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(jwt -> {
			Collection<GrantedAuthority> authorities = new ArrayList<>(scopeConverter.convert(jwt));

			// Grant USER role when the token carries the coop read scope
			String scopeClaim = jwt.getClaimAsString("scope");
			if (scopeClaim != null && scopeClaim.contains(COOP_READ_SCOPE)) {
				authorities.add(new SimpleGrantedAuthority(Settings.REG_USER_ROLE));
			}

			return authorities;
		});
		return converter;
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new MyAuthEntryPoint();
	}

	// Set the used firewall to the most lax possible safety settings.
	@Bean
	public HttpFirewall constructFairlyOpenFirewall() {
		DefaultHttpFirewall firewall = new DefaultHttpFirewall();
		firewall.setAllowUrlEncodedSlash(true);
		return firewall;
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web
			.httpFirewall(constructFairlyOpenFirewall());
	}
}
