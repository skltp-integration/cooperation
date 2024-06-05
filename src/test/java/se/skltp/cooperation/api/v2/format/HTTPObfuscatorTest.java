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
package se.skltp.cooperation.api.v2.format;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class HTTPObfuscatorTest {

	private HTTPObfuscator uut;

	@Before
	public void setUp() throws Exception {
		uut = new HTTPObfuscatorImpl();

	}


	@Test
	public void test() throws Exception {
		verify("http://abcdef.se","http://....def.se");
		verify("http://abcdefghij.se/","http://....hij.se");
		verify("http://abcdefghij.se:443","http://....hij.se....443");
		verify("http://abcdefghij.se:443/adapter/npo/npo/v1","http://....hij.se....443..../v1");
		verify("https://abcdefghij.se:443/adapter/npo/npo/v1","https://....hij.se....443..../v1");
		verify("http://abcdefghij.se/adapter/npo/npo/v1","http://....hij.se..../v1");
	}


	private void verify(String original, String expectedResult) {
		assertEquals(expectedResult,uut.obfuscate(original));
	}

}
