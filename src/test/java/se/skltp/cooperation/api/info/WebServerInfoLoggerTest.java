package se.skltp.cooperation.api.info;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.slf4j.Logger;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static org.mockito.Mockito.*;

class WebServerInfoLoggerTest {
	@DisplayName("Logs the specific address and port bound")
	@Test
	void testSpecificBindAddress() {
		WebServerInfoLogger webServerInfoLogger = new WebServerInfoLogger("127.0.0.1");
		webServerInfoLogger.log = mock(Logger.class);
		WebServerInitializedEvent event = mock(WebServerInitializedEvent.class);
		WebServer webServer = mock(WebServer.class);
		when(event.getWebServer()).thenReturn(webServer);
		when(webServer.getPort()).thenReturn(4242);

		webServerInfoLogger.onApplicationEvent(event);

		verify(webServerInfoLogger.log).info(anyString(), eq("127.0.0.1"), eq(4242));
	}

	@DisplayName("Logs all interfaces if bound to *")
	@Test
	void testBindToAllInterfaces() throws SocketException {
		WebServerInfoLogger webServerInfoLogger = new WebServerInfoLogger("0.0.0.0");
		webServerInfoLogger.log = mock(Logger.class);
		WebServerInitializedEvent event = mock(WebServerInitializedEvent.class);
		WebServer webServer = mock(WebServer.class);
		when(event.getWebServer()).thenReturn(webServer);
		when(webServer.getPort()).thenReturn(4242);

		try (MockedStatic<NetworkInterface> networkInterface = mockStatic(NetworkInterface.class)) {
			Enumeration<NetworkInterface> networkInterfaces = getNetworkInterfaces();
			networkInterface.when(NetworkInterface::getNetworkInterfaces).thenReturn(networkInterfaces);
			webServerInfoLogger.onApplicationEvent(event);

		}

		verify(webServerInfoLogger.log).info(anyString(), eq("if1"), eq("192.168.10.11"), eq(4242));
		verify(webServerInfoLogger.log).info(anyString(), eq("if1"), eq("fd12:3456:789a::/48"), eq(4242));
		verify(webServerInfoLogger.log).info(anyString(), eq("if2"), eq("192.168.10.12"), eq(4242));
		verify(webServerInfoLogger.log).info(anyString(), eq("if2"), eq("fd89:abcd:ef01::/48"), eq(4242));
	}

	@DisplayName("Exceptions are handled gracefully")
	@Test
	void testBindToAllInterfacesException() throws SocketException {
		WebServerInfoLogger webServerInfoLogger = new WebServerInfoLogger("0.0.0.0");
		webServerInfoLogger.log = mock(Logger.class);
		WebServerInitializedEvent event = mock(WebServerInitializedEvent.class);
		WebServer webServer = mock(WebServer.class);
		when(event.getWebServer()).thenReturn(webServer);
		when(webServer.getPort()).thenReturn(4242);

		try (MockedStatic<NetworkInterface> networkInterface = mockStatic(NetworkInterface.class)) {
			NetworkInterface if1 = mock(NetworkInterface.class);
			when(if1.isUp()).thenThrow(SocketException.class);
			List<NetworkInterface> networkInterfaces = List.of(if1);
			networkInterface.when(NetworkInterface::getNetworkInterfaces).thenReturn(Collections.enumeration(networkInterfaces));
			webServerInfoLogger.onApplicationEvent(event);
		}

		verify(webServerInfoLogger.log).warn(anyString(), any(Throwable.class));
	}

	private static Enumeration<NetworkInterface> getNetworkInterfaces() throws SocketException {
		NetworkInterface if1 = getNetworkInterface("if1", "192.168.10.11", "fd12:3456:789a::/48");
		NetworkInterface if2 = getNetworkInterface("if2", "192.168.10.12", "fd89:abcd:ef01::/48");
		List<NetworkInterface> networkInterfaces = List.of(if1, if2);
		return Collections.enumeration(networkInterfaces);
	}

	private static NetworkInterface getNetworkInterface(String ifName, String... hostAddresses) throws SocketException {
		NetworkInterface if1 = mock(NetworkInterface.class);
		when(if1.isUp()).thenReturn(true);
		when(if1.isLoopback()).thenReturn(false);
		when(if1.getName()).thenReturn(ifName);
		List<InetAddress> inetAddresses = new ArrayList<>();
		for (String hostAddress: hostAddresses) {
			InetAddress inetAddress = mock(InetAddress.class);
			when(inetAddress.getHostAddress()).thenReturn(hostAddress);
			inetAddresses.add(inetAddress);
		}
		when(if1.getInetAddresses()).thenReturn(Collections.enumeration(inetAddresses));
		return if1;
	}
}
