package se.skltp.cooperation.util;

import java.time.Duration;
import java.time.Instant;

public class TimeDiffUtil {

	Instant start;

	public TimeDiffUtil() {
		start = Instant.now();
	}
	
	public long timeElapsed() {
		return Duration.between(start, Instant.now()).toMillis()/1000;
	}
}
