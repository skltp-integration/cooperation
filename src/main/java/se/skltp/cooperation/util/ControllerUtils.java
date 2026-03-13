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
