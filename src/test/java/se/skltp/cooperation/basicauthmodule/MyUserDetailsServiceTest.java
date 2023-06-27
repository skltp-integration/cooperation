package se.skltp.cooperation.basicauthmodule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MyUserDetailsServiceTest {

	@Test
	void whenTestingPassword_goodAndBadPassword() {
		assertFalse(MyUserDetailsService.isBadPassword("hdKq53sA2j"));
		assertTrue(MyUserDetailsService.isBadPassword("qwerty")); //short, lacks num, lacks upper.
		assertTrue(MyUserDetailsService.isBadPassword("qwerty12345")); //lacks upper case
		assertTrue(MyUserDetailsService.isBadPassword("AB12CD34")); //lacks lower case
	}
}
