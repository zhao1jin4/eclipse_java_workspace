package jdk8_new.date;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class ClockTest {
	public static void main(String[] args) {

		Clock clock = Clock.systemDefaultZone();
		long millis = clock.millis();
		
		Instant instant = clock.instant();
		Date legacyDate = Date.from(instant);   // legacy java.util.Date
		
		

		LocalTime now1 = LocalTime.now(ZoneId.systemDefault());


LocalTime late = LocalTime.of(23, 59, 59);
		
	}
}
