/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import se.skltp.cooperation.Application;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class Version1GoneTest {
	@Autowired
	private Version1Gone version1Gone;

	@Test
	public void getAnyVersion1Resource_shouldBeToldIsGone() throws Exception {
    	assertEquals("ResponseEntity", version1Gone.respondWithGone().getClass().getSimpleName(),"Response must be of Type 'ResponseEntity'");
		ResponseEntity<String> result = version1Gone.respondWithGone();
    	assertSame(HttpStatus.GONE, result.getStatusCode(), "Response must be of type HTTP 410 GONE.");
	}
}
