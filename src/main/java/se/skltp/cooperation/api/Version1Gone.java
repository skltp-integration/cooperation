/*
 * Copyright © 2015-2026 Inera.
 * Copyright owner URL: https://www.inera.se/
 * TAK-API (Cooperation) overview page: https://inera.atlassian.net/wiki/spaces/NTJPP/pages/3359539201/Ineras+Informationstj+nster
 * This library is free software under the GNU Lesser General Public License v2.1 or later.
 * Please refer to the full license files at the project root.
 */
package se.skltp.cooperation.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a basic catch-all Response to tell any callers that an API has been closed.
 */
@RestController
@RequestMapping(value = {
	"/api/v1/**"
})
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
