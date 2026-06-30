/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.info;

import jakarta.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Component
public class ControllerLogger implements ApplicationListener<ApplicationReadyEvent> {

	Logger log = LoggerFactory.getLogger(ControllerLogger.class);
	private final ServletContext servletContext;

	public ControllerLogger(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
		if (!log.isDebugEnabled()) {
			return;
		}
		final String contextPath = getContextPath();
		ApplicationContext context = event.getApplicationContext();

		Map<String, Object> controllers = context.getBeansWithAnnotation(Controller.class);

		log.debug("Controllers:");

		controllers.values().forEach(controller -> {
			Class<?> type = controller.getClass();
			RequestMapping mapping = AnnotatedElementUtils.findMergedAnnotation(type, RequestMapping.class);

			if (mapping != null) {
				StringBuilder sb = new StringBuilder();
				boolean first = true;
				for (String path : mapping.value()) {
					if (!first) {
						sb.append(", ");
					}
					first = false;
					sb.append(contextPath).append(path);
				}
				log.debug("  {} -> {}", sb, type.getSimpleName());
			}
		});
	}

	private String getContextPath() {
		String contextPath = servletContext.getContextPath();
		if (contextPath.endsWith("/")) {
			contextPath = contextPath.substring(0, contextPath.length() - 1);
		}
		return contextPath;
	}
}

