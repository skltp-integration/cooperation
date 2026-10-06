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

class ServiceConsumerCriteriaTest {

	private ServiceConsumerCriteria uut;

	@BeforeEach
	void setUp() {
		uut = new ServiceConsumerCriteria();
	}

	@Test
	void isEmpty_shouldBeEmpty() {
		assertTrue(uut.isEmpty());
	}

	@Test
	void isEmpty_shouldNotBeEmpty() {
		uut.setConnectionPointId(1L);
		assertFalse(uut.isEmpty());
	}
}
