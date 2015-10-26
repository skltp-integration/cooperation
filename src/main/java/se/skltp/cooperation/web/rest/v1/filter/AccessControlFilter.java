/**
 * Copyright (c) 2014 Center for eHalsa i samverkan (CeHis).
 * 								<http://cehis.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package se.skltp.cooperation.web.rest.v1.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/*
 * A Servlet Filter that will check if the api_key String is present in the Request.
 * Implements an extremely basic access limitation.
 * Need to be replaced by a better solution if this application is to be considered a permanent one.
 * 
 * Technical note :
 *    A filter will not work with the @ControllerAdvice in the DefaultExceptionHandler
 *    http://stackoverflow.com/questions/30335157/make-simple-servlet-filter-work-with-controlleradvice 
 */
@Component
@EnableAutoConfiguration
@EnableConfigurationProperties
public class AccessControlFilter implements Filter {

	private static final String API_KEY = "api_key";

	private final Logger log = LoggerFactory.getLogger(AccessControlFilter.class);

	@Value("${se.skltp.cooperation.accesscontrol}")
	private Boolean accesscontrol;

	@Value("${se.skltp.cooperation.api_key}")
	private String api_key;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		log.debug("AccessControl is " + accesscontrol + request.getParameter(API_KEY) + " header: "
				+ request.getHeader(API_KEY));

		if (accesscontrol) {
			if (request.getRequestURI().contains("/v1/")) {
				if (!api_key.equals(request.getHeader(API_KEY))) {
					if (!api_key.equals(request.getParameter(API_KEY))) {
						response.sendError(HttpServletResponse.SC_FORBIDDEN,
								"Nonexisting or invalid API key");
						return;
					}
				}
			}
		}

		chain.doFilter(request, res);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
