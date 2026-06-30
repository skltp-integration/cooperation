/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.v2.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HTTPObfuscatorImpl implements HTTPObfuscator {

	private static final Object DOTS = "....";

	private final Logger log = LoggerFactory.getLogger(HTTPObfuscatorImpl.class);

	@Override
	public String obfuscate(String original) {
		// Obfuscate an URL because some people do not want it to be public
		// Specification:
		// before - https://esb.ntjp.sjunet.org:443/adapter/npo/npo/v1
		// after - https://....unet.org....443..../v1

		try {
			StringBuffer buffer = new StringBuffer();
			int doubleSlashPosition = original.indexOf("//");
			if (doubleSlashPosition < 0 || original.length() < doubleSlashPosition + 2) {
				return original;
			}
			buffer.append(original.substring(0, doubleSlashPosition + 2));
			buffer.append(DOTS);
			String remainder = original.substring(doubleSlashPosition + 2);
			int semicolonPosition = remainder.indexOf(":");
			int slashPosition = remainder.indexOf("/");
			if (semicolonPosition > 0) {
				buffer.append(remainder.substring(Math.max(semicolonPosition - 6,0), semicolonPosition));
				buffer.append(DOTS);
				if (slashPosition > 0){
					buffer.append(remainder.substring(semicolonPosition + 1, slashPosition));
				} else{
					buffer.append(remainder.substring(semicolonPosition + 1));
				}

			} else if (slashPosition > 0) {
				buffer.append(remainder.substring(Math.max(slashPosition - 6,0), slashPosition));
			} else {
				buffer.append(remainder.substring(Math.max(remainder.length() - 6, 0)));
			}
			int lastIndexSlash = remainder.lastIndexOf("/");
			if ( lastIndexSlash > 0 && remainder.length() > lastIndexSlash +1){
				buffer.append(DOTS);
				buffer.append(remainder.substring(lastIndexSlash));
			}

			return buffer.toString();
		} catch (Exception e) {
			log.info("Failed to obfuscate: " + original);
			return original;
		}
	}

}
