/**
 * Copyright (c) 2015-2026 Inera.
 * * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.v2.format;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HTTPObfuscatorTest {

	private HTTPObfuscator uut;

	@BeforeEach
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
