package se.skltp.cooperation.api.info;

import jakarta.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ServletMappingLogger implements ApplicationListener<ApplicationReadyEvent> {

	Logger log = LoggerFactory.getLogger(ServletMappingLogger.class);

	private final ServletContext servletContext;

	public ServletMappingLogger(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
		if (!log.isDebugEnabled()) {
			return;
		}
		var servletRegistrations = servletContext.getServletRegistrations();
		final String contextPath = getContextPath();
		if (!servletRegistrations.isEmpty()) {
			log.debug("Servlets:");
			servletContext.getServletRegistrations().forEach((name, registration) ->
				registration.getMappings().forEach(mapping -> log.debug("  {}{} -> {}", contextPath, mapping, name)
			));
		}
	}
	private String getContextPath() {
		String contextPath = servletContext.getContextPath();
		if (contextPath.endsWith("/")) {
			contextPath = contextPath.substring(0, contextPath.length() - 1);
		}
		return contextPath;
	}
}
