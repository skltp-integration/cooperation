/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;

import org.springframework.context.event.EventListener;
import org.springframework.core.SpringVersion;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * This function exists specifically to do some simple logging of what Spring Boot and Spring Framework versions
	 * are being used in the application. When the Spring Boot banner is hidden in an app, this is otherwise only done
	 * through a debug-level log-entry from Spring Boot. This function thus helps make clear versions through logging,
	 * as the application undergoes normal lifecycle upgrades.
	 */
	@EventListener(ApplicationStartedEvent.class)
	void springLogger() {
		final Logger log = LoggerFactory.getLogger(Application.class);
		log.info("Application Launching with Spring Boot v{}, Spring v{}",
			SpringBootVersion.getVersion(),
			SpringVersion.getVersion());
	}
}
