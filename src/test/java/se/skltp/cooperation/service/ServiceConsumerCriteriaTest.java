/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.service;


import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 */
public class ServiceConsumerCriteriaTest {

	private ServiceConsumerCriteria uut;

	@BeforeEach
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
