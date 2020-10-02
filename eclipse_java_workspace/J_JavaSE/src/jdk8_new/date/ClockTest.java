package jdk8_new.date;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

public class ClockTest {
	public static void main(String[] args) {

		Clock clock = Clock.systemDefaultZone();
		long millis = clock.millis();
		
		Instant instant = clock.instant();
		Date legacyDate = Date.from(instant);   // legacy java.util.Date
		
		Instant.ofEpochMilli(System.currentTimeMillis());
		Instant.parse("1995-10-23T10:22:22Z");
		Instant after=Instant.now().plus(1,ChronoUnit.DAYS); //GMT时间
		

		LocalTime now1 = LocalTime.now(ZoneId.systemDefault());


		LocalTime late = LocalTime.of(23, 59, 59);
		
		
		TimeZone.getTimeZone("GMT+8").getID();
		TimeZone.getTimeZone("GMT+8");//方式一
		TimeZone.getTimeZone(ZoneId.of("Asia/Shanghai"));//方式二
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
		String[] countryCities=TimeZone.getAvailableIDs();//所有 大州/市
		System.out.println(Arrays.toString(countryCities));
		
		ZonedDateTime m=java.time.ZonedDateTime.parse("2017-01-20T17:42:47.789+08:00[Asia/Shanghai]");
		System.out.println(m);
	}
}
