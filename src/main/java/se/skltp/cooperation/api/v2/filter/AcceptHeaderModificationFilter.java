/**
 * Copyright (c) 2015-2026 Inera.
 * * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.v2.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
 * A Servlet Filter that will add Accept-headers for json or xml if the URI string contains .json or .xml.
 * The serialization of object return by the GET methods of the REST api depends on which Accept-headar
 * that is present in the reuest. This it the general solution in this api to support .json and .xml.
 * Since the header information is read-only in the request a Request Wrapper is created containing the
 * extra header information. The wrapper extends jakarta.servlet.http.HttpServletRequestWrapper.
 */
@Component
public class AcceptHeaderModificationFilter implements Filter {

	private final Logger log = LoggerFactory.getLogger(AcceptHeaderModificationFilter.class);

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;

		HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(request);
		String requestURI = request.getRequestURI();
		if (requestURI.contains(".json")) {
			requestWrapper.addHeader("Accept", "application/json");
		}
		if (requestURI.contains(".xml")) {
			requestWrapper.addHeader("Accept", "application/xml");
		}

		chain.doFilter(requestWrapper, res);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
		/**
		 * construct a wrapper for this request
		 *
		 * @param request
		 */
		public HeaderMapRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		private Map<String, String> headerMap = new HashMap<String, String>();

		/**
		 * add a header with given name and value
		 *
		 * @param name
		 * @param value
		 */
		public void addHeader(String name, String value) {
			headerMap.put(name, value);
		}

		@Override
		public String getHeader(String name) {
			String headerValue = super.getHeader(name);
			if (headerMap.containsKey(name)) {
				headerValue = headerMap.get(name);
			}
			return headerValue;
		}

		/**
		 * get the Header names
		 */
		@Override
		public Enumeration<String> getHeaderNames() {
			List<String> names = Collections.list(super.getHeaderNames());
			for (String name : headerMap.keySet()) {
				names.add(name);
			}
			return Collections.enumeration(names);
		}

		@Override
		public Enumeration<String> getHeaders(String name) {
			List<String> values = Collections.list(super.getHeaders(name));
			if (headerMap.containsKey(name)) {
				values.add(headerMap.get(name));
			}
			return Collections.enumeration(values);
		}

	}

}
