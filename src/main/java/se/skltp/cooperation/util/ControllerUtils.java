/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class ControllerUtils {

	private static final Pattern COMMA_PATTERN = Pattern.compile(",");
	private ControllerUtils() {	}

	public static List<String> splitCommaSeparated(String input) {
		if (input == null || input.isBlank()) {
			return List.of();
		}

		return COMMA_PATTERN.splitAsStream(input)
			.map(String::trim)
			.filter(s -> !s.isEmpty())
			.toList();
	}

	public static <T> List<T> iterableToArrayList(Iterable<T> iterable) {
		List<T> result = new ArrayList<>();
		iterable.forEach(result::add);
		return result;
	}
}
