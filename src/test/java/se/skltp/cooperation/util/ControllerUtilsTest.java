package se.skltp.cooperation.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class ControllerUtilsTest {

	@Test
	void splitCommaSeparated_shouldReturnEmptyListForNullInput() {
		List<String> result = ControllerUtils.splitCommaSeparated(null);

		assertTrue(result.isEmpty());
	}

	@Test
	void splitCommaSeparated_shouldReturnEmptyListForBlankInput() {
		List<String> result = ControllerUtils.splitCommaSeparated("   ");

		assertTrue(result.isEmpty());
	}

	@Test
	void splitCommaSeparated_shouldSplitTrimAndOmitEmptyEntries() {
		List<String> result = ControllerUtils.splitCommaSeparated(" one, two , , three,  ,four ");

		assertEquals(List.of("one", "two", "three", "four"), result);
	}

	@Test
	void splitCommaSeparated_shouldPreserveOrderAndDuplicates() {
		List<String> result = ControllerUtils.splitCommaSeparated("a, b, a , c");

		assertEquals(List.of("a", "b", "a", "c"), result);
	}

	@Test
	void splitCommaSeparated_shouldHandleSingleValue() {
		List<String> result = ControllerUtils.splitCommaSeparated("value");

		assertEquals(List.of("value"), result);
	}

	@Test
	void iterableToArrayList_shouldCopyElementsFromIterable() {
		Iterable<String> iterable = List.of("one", "two", "three");

		List<String> result = ControllerUtils.iterableToArrayList(iterable);

		assertEquals(List.of("one", "two", "three"), result);
	}

	@Test
	void iterableToArrayList_shouldReturnEmptyListForEmptyIterable() {
		Iterable<String> iterable = List.of();

		List<String> result = ControllerUtils.iterableToArrayList(iterable);

		assertTrue(result.isEmpty());
	}

	@Test
	void iterableToArrayList_shouldReturnMutableArrayList() {
		Iterable<String> iterable = List.of("one", "two");

		List<String> result = ControllerUtils.iterableToArrayList(iterable);

		assertInstanceOf(ArrayList.class, result);
		assertDoesNotThrow(() -> result.add("three"));
		assertEquals(List.of("one", "two", "three"), result);
	}
}
