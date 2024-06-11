package se.skltp.cooperation.api;

import org.apache.catalina.security.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import se.skltp.cooperation.Application;

import static org.junit.Assert.*;

@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = SecurityConfig.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class Version1GoneTest {
	@Autowired
	private Version1Gone version1Gone;

	@Test
	public void getAnyVersion1Resource_shouldBeToldIsGone() throws Exception {
    	assertEquals("Response must be of Type 'ResponseEntity'", "ResponseEntity", version1Gone.respondWithGone().getClass().getSimpleName());
		ResponseEntity<String> result = version1Gone.respondWithGone();
    	assertSame("Response must be of type HTTP 410 GONE.", HttpStatus.GONE, result.getStatusCode());
	}
}
