package se.skltp.cooperation.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Peter Merikan
 */
public class ServiceConsumerCriteriaTest {

	private ServiceConsumerCriteria uut;

	@Before
	public void setUp() throws Exception {
		uut = new ServiceConsumerCriteria();
	}

	@Test
	public void isEmpty_shouldBeEmpty() throws Exception {
		assertTrue(uut.isEmpty());
	}

	@Test
	public void isEmpty_shouldNotBeEmpty() throws Exception {
		uut.setConnectionPointId(1L);
		assertFalse(uut.isEmpty());
	}
}
