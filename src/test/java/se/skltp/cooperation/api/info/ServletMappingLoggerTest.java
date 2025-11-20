package se.skltp.cooperation.api.info;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

class ServletMappingLoggerTest {
	@DisplayName("Does nothing if the debug level isn't active")
	@Test
	void testDebugInactive() {
		ServletContext servletContext = mock(ServletContext.class);
		ServletMappingLogger servletMappingLogger = new ServletMappingLogger(servletContext);
		servletMappingLogger.log = mock(Logger.class);
		when(servletMappingLogger.log.isDebugEnabled()).thenReturn(false);

		ApplicationReadyEvent event = mock(ApplicationReadyEvent.class);
		servletMappingLogger.onApplicationEvent(event);

		verify(servletMappingLogger.log, times(0)).debug(anyString(), any(), any(), any());
	}

	@DisplayName("Logs registered servlet mappings")
	@Test
	void testLogServlets() {
		ServletContext servletContext = mock(ServletContext.class);
		when(servletContext.getContextPath()).thenReturn("/coop/");
		Map<String, ServletRegistration> servletRegistrations = new HashMap<>();
		servletRegistrations.put("s1", getServletRegistration("/a/b/c", "/e/f/g"));
		servletRegistrations.put("s2", getServletRegistration("/h/i/j", "/k/l/m"));
		doReturn(servletRegistrations).when(servletContext).getServletRegistrations();
		ServletMappingLogger servletMappingLogger = new ServletMappingLogger(servletContext);
		servletMappingLogger.log = mock(Logger.class);
		when(servletMappingLogger.log.isDebugEnabled()).thenReturn(true);

		ApplicationReadyEvent event = mock(ApplicationReadyEvent.class);
		servletMappingLogger.onApplicationEvent(event);

		verify(servletMappingLogger.log).debug(anyString(), eq("/coop"), eq("/a/b/c"), eq("s1"));
		verify(servletMappingLogger.log).debug(anyString(), eq("/coop"), eq("/e/f/g"), eq("s1"));
		verify(servletMappingLogger.log).debug(anyString(), eq("/coop"), eq("/h/i/j"), eq("s2"));
		verify(servletMappingLogger.log).debug(anyString(), eq("/coop"), eq("/k/l/m"), eq("s2"));
	}

	private static ServletRegistration getServletRegistration(String... paths) {
		ServletRegistration servletRegistration1 = mock(ServletRegistration.class);
		when(servletRegistration1.getMappings()).thenReturn(List.of(paths));
		return servletRegistration1;
	}
}
