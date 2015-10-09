/**
 * Copyright (c) 2014 Center for eHalsa i samverkan (CeHis).
 * 								<http://cehis.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
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
