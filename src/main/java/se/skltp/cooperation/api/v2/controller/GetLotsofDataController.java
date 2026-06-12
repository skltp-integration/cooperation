/**
 * Copyright (c) 2015-2026 Inera.
 * * This library is free software under the GNU Lesser General Public License v2.1.
 * Refer to the full license files at the project root.
 */
package se.skltp.cooperation.api.v2.controller;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = {
	"/api/v2/lotsOfData",
	"/api/v2/lotsOfData/"
})
public class GetLotsofDataController {

	private static final Logger log = LoggerFactory.getLogger(GetLotsofDataController.class);

	private static final int MIN_MB = 1;
	private static final int MAX_MB = 101;
	private static final long ONE_MB = 1024L * 1024L;
	private static final byte[] CHUNK_BYTES = createChunk();

	@GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<StreamingResponseBody> getLotsOfData(@RequestParam int mb) {
		if (mb < MIN_MB || mb > MAX_MB) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Query parameter 'mb' must be between " + MIN_MB + " and " + MAX_MB + ".");
		}

		final long totalBytes = mb * ONE_MB;
		log.debug("REST request to stream {} MB dummy text ({} bytes)", mb, totalBytes);

		StreamingResponseBody stream = outputStream -> {
			long remaining = totalBytes;
			while (remaining > 0) {
				int bytesThisRound = (int) Math.min(CHUNK_BYTES.length, remaining);
				outputStream.write(CHUNK_BYTES, 0, bytesThisRound);
				remaining -= bytesThisRound;
			}
			outputStream.flush();
		};

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));
		headers.set(HttpHeaders.CONTENT_ENCODING, "identity");
		headers.setCacheControl(CacheControl.noStore().getHeaderValue());

		// Deliberately skip Content-Length to enable chunked transfer streaming.
		return new ResponseEntity<>(stream, headers, HttpStatus.OK);
	}

	private static byte[] createChunk() {
		byte[] chunk = new byte[8192];
		byte[] pattern = "Dummy text chunk for load testing.\n".getBytes(StandardCharsets.UTF_8);
		for (int i = 0; i < chunk.length; i++) {
			chunk[i] = pattern[i % pattern.length];
		}
		return Arrays.copyOf(chunk, chunk.length);
	}
}


