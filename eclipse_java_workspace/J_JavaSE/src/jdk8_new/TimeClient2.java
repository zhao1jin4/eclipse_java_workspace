package jdk8_new;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public interface TimeClient2 {
	 
	static ZoneId getZoneId(String zoneString) { 
		 System.out.println("在接口TimeClient2中");
		try {
			return ZoneId.of(zoneString);
		} catch (DateTimeException e) {
			System.err.println("Invalid time zone: " + zoneString
					+ "; using default time zone instead.");
			return ZoneId.systemDefault();
		}
	}
	
	 default  ZonedDateTime getZonedDateTime(String zoneString) {
		 System.out.println("在接口TimeClient2中");
		 return null;
	}
}