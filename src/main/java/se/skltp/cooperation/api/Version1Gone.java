package se.skltp.cooperation.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {
	"/api/v1/**"
})
/**
 * This is a basic catch-all Response to tell any callers that an API has been closed.
 */
public class Version1Gone {
	@GetMapping()
	public ResponseEntity<String> respondWithGone() {
		return new ResponseEntity<>(
			"Cooperation API v1 has been closed.\n" +
				"Please use v2 instead.\n" +
				"For more information about changes needed for v2, contact API maintainer and provider.",
			HttpStatus.GONE);
	}
}
