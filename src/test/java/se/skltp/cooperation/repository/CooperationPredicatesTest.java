package se.skltp.cooperation.repository;

import com.mysema.query.types.Predicate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link CooperationPredicates}
 *
 * @author Peter Merikan
 */
public class CooperationPredicatesTest {

	@Test
	public void testServiceConsumerIdIs() throws Exception {
		Predicate predicate = CooperationPredicates.serviceConsumerIdIs(5L);
		String predicateAsString = predicate.toString();
		assertEquals("cooperation.serviceConsumer.id = 5", predicateAsString);
	}

	@Test
	public void testLogicalAddresIdIs() throws Exception {
		Predicate predicate = CooperationPredicates.logicalAddressIdIs(4L);
		String predicateAsString = predicate.toString();
		assertEquals("cooperation.logicalAddress.id = 4", predicateAsString);
	}

	@Test
	public void testServiceContractIdIs() throws Exception {
		Predicate predicate = CooperationPredicates.serviceContractIdIs(3L);
		String predicateAsString = predicate.toString();
		assertEquals("cooperation.serviceContract.id = 3", predicateAsString);
	}

	@Test
	public void testConnectionPointIdIs() throws Exception {
		Predicate predicate = CooperationPredicates.connectionPointIdIs(2L);
		String predicateAsString = predicate.toString();
		assertEquals("cooperation.connectionPoint.id = 2", predicateAsString);
	}
}
