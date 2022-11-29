//////
// 2022-05-17, Henrik Augustsson.
// Nordic Medtest.
//////

package se.skltp.cooperation.basicauthmodule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.DefaultHttpFirewall;

@Configuration
@EnableWebSecurity
public class BasicAuthConfig extends WebSecurityConfigurerAdapter {

	@Bean
	protected MyUserDetailsService userDetailsService() {
		return new MyUserDetailsService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	// Authorization : Role -> Access
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.csrf().disable()
			.authorizeRequests()

			// For the two versions of the primary API.
			.antMatchers("/api/v2/**").hasAnyAuthority("ADMIN", "USER")
			.antMatchers("/api/v1/**").permitAll()

			// For the two test endpoints used to test Spring Boot Security configuration.
			.antMatchers("/authoring/api/v2/**").hasAnyAuthority("ADMIN", "USER")
			.antMatchers("/authoring/api/v1/**").permitAll()

			// The swagger Docs are open outward.
			.antMatchers("/doc/**").permitAll()

			// Ping always responds openly.
			.antMatchers("/authoring/ping").permitAll()

			// Roles assumed to inherit hierarchically.
			.antMatchers(Settings.authAdministrationSubPath + "/user/**").hasAnyAuthority("ADMIN", "USER")
			.antMatchers(Settings.authAdministrationSubPath + "/admin/**").hasAuthority("ADMIN")

			/*
			// Examples of how to configure user/admin-differentiated URLs.
			.antMatchers("/user/**").hasAnyAuthority("ADMIN", "USER") // Roles assumed to inherit hierarchically.
			.antMatchers("/admin/**").hasAuthority("ADMIN")
			 */

			// Default level for all other endpoints is assumed restricted.
			.antMatchers("/**").hasAnyAuthority("ADMIN", "USER")

			.anyRequest().authenticated()
			.and()
			.httpBasic()
		;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
		throws Exception {
		auth.userDetailsService(userDetailsService());
		auth.authenticationProvider(authenticationProvider());
	}

	// Set the used firewall to the most lax possible safety settings.
	@Bean
	public HttpFirewall constructFairlyOpenFirewall() {
		DefaultHttpFirewall firewall = new DefaultHttpFirewall();
		firewall.setAllowUrlEncodedSlash(true);
		return firewall;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
		web.httpFirewall(constructFairlyOpenFirewall());
	}
}
