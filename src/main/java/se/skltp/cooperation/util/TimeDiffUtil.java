/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.util;

import java.time.Duration;
import java.time.Instant;

public class TimeDiffUtil {

	Instant start;

	public TimeDiffUtil() {
		start = Instant.now();
	}
	
	public long timeElapsed() {
		return Duration.between(start, Instant.now()).toMillis()/1000;
	}
}
