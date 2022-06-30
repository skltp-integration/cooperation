package se.skltp.cooperation.basicauthmodule;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller exists only to present a few more endpoints for tests outside of the scope of the auth controller.
 * Can happily be annihilated if unneeded.
 */
// @RestController
public class OtherController {

	// @GetMapping("/aaa")
	public String hello1() {
		return "Pong!";
	}

	// @GetMapping("/aaa/b")
	public String hello2() {
		return "Pong!";
	}

	// @GetMapping("/bbb/ccc")
	public String hello3() {
		return "Pong!";
	}
}
