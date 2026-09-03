/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.context.WebServerInitializedEvent;
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
