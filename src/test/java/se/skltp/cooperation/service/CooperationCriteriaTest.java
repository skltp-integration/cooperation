package se.skltp.cooperation.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Peter Merikan
 */
public class CooperationCriteriaTest {

	private CooperationCriteria uut;

	@Before
	public void setUp() throws Exception {
		uut = new CooperationCriteria();

	}

	@Test
	public void isEmpty_shouldBeEmpty() throws Exception {
		assertTrue(uut.isEmpty());
	}

	@Test
	public void isEmpty_shouldNotBeEmpty() throws Exception {
		uut.setConnectionPointId(1L);
		assertFalse(uut.isEmpty());
		uut.setLogicalAddressId(1L);
		assertFalse(uut.isEmpty());
		uut.setServiceConsumerId(1L);
		assertFalse(uut.isEmpty());
		uut.setServiceContractId(1L);
		assertFalse(uut.isEmpty());
	}

}
