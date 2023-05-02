package se.skltp.cooperation.basicauthmodule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

/**
 * This controller exists only to present a few more endpoints for tests outside of the scope of the auth controller.
 * Can happily be annihilated if unneeded.
 */
// @RestController
public class OtherController {

	@Autowired
	Settings settings;

	////
	// TEST IMPLEMENT OF V1 AND V2
	////

	@GetMapping("/api/v1/test") // v1 is to be open access.
	public String v1getter(){
		return "I'm Open and Accessible!";
	}

	@GetMapping("/api/v2/test") // v2 is to be set behind Basic Authorization.
	public String v2getter() {
		return "I'm hidden behind Auth!";
	}

	////
	// Pings and Hellos.
	////

	@GetMapping("/ping")
	public String hello() {
		if (!settings.allowApi_hellosAndPings) {
			throw new ResponseStatusException(
				HttpStatus.LOCKED, "Not Available."
			);
		}
		return "Pong!";
	}
}
