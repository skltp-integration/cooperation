/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.info;

import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ControllerLoggerTest {
	@DisplayName("Does nothing if the debug level isn't active")
	@Test
	void testDebugInactive() {
		ServletContext servletContext = mock(ServletContext.class);
		ControllerLogger controllerLogger = new ControllerLogger(servletContext);
		controllerLogger.log = mock(Logger.class);
		when(controllerLogger.log.isDebugEnabled()).thenReturn(false);

		ApplicationReadyEvent event = mock(ApplicationReadyEvent.class);
		controllerLogger.onApplicationEvent(event);

		verify(controllerLogger.log, times(0)).debug(anyString(), any(), any(), any());
	}
}
