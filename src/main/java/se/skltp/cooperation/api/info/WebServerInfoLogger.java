package se.skltp.cooperation.api.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

@Component
public class WebServerInfoLogger implements ApplicationListener<WebServerInitializedEvent> {

	Logger log = LoggerFactory.getLogger(WebServerInfoLogger.class);

	private final String bindAddress;

	public WebServerInfoLogger(@Value("${server.address:0.0.0.0}") String bindAddress) {
		this.bindAddress = bindAddress;
	}

	@Override
	public void onApplicationEvent(WebServerInitializedEvent event) {
		int port = event.getWebServer().getPort();

		log.info("Web server started:");

		if (!"0.0.0.0".equals(bindAddress) && !"::".equals(bindAddress)) {
			log.info("  {}:{}", bindAddress, port);
			return;
		}

		try {
			Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
			while (nets.hasMoreElements()) {
				NetworkInterface iface = nets.nextElement();
				if (!iface.isUp() || iface.isLoopback()) {
					continue;
				}

				Enumeration<InetAddress> addrs = iface.getInetAddresses();
				while (addrs.hasMoreElements()) {
					InetAddress addr = addrs.nextElement();
					log.info("  {}: {}:{}", iface.getName(), addr.getHostAddress(), port);
				}
			}
		} catch (Exception e) {
			log.warn("Could not enumerate interfaces", e);
		}
	}
}
