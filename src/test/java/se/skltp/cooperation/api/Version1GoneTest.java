package se.skltp.cooperation.api;

import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import se.skltp.cooperation.Application;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = SecurityConfig.class)
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
