package jdk8_new;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SimpleTimeClient implements TimeClient ,TimeClient2{

	private LocalDateTime dateAndTime; //jdk8新类

	public SimpleTimeClient() {
		dateAndTime = LocalDateTime.now();
	}

	public void setTime(int hour, int minute, int second) {
		LocalDate currentDate = LocalDate.from(dateAndTime); 
		LocalTime timeToSet = LocalTime.of(hour, minute, second);//of表示修改
		dateAndTime = LocalDateTime.of(currentDate, timeToSet);
	}

	public void setDate(int year, int month, int day) {
		LocalDate dateToSet = LocalDate.of(year, month, day);
		LocalTime currentTime = LocalTime.from(dateAndTime);
		dateAndTime = LocalDateTime.of(dateToSet, currentTime);
	}

	public void setDateAndTime(int year, int month, int day, int hour,
			int minute, int second) {
		LocalDate dateToSet = LocalDate.of(year, month, day);
		LocalTime timeToSet = LocalTime.of(hour, minute, second);
		dateAndTime = LocalDateTime.of(dateToSet, timeToSet);
	}

	public LocalDateTime getLocalDateTime() {
		return dateAndTime;
	}

	public String toString() {
		return dateAndTime.toString();
	}

	
	//只可重写default的(可以不重写),不能重写static 的
	//一个类继承两个接口时,这个两个接口中如有相同的default方法,子类必须重写
	@Override
	public ZonedDateTime getZonedDateTime(String zoneString) {
		System.out.println("default在类SimpleTimeClient中");
		return TimeClient.super.getZonedDateTime(zoneString);
		//return null;
	}
	
	//同名方法不是重写,实现优先
	public 	static ZoneId getZoneId(String zoneString) { 
		System.out.println(" static 在SimpleTimeClient类中");
		return ZoneId.systemDefault();
	}
	
	public static void main(String... args) {
		SimpleTimeClient client = new SimpleTimeClient();
		// 显示当前日期时间
		System.out.println(client.toString());
		// 设置日期
		client.setTime(11, 12, 22);
		System.out.println(client);
		// 设置时间
		client.setDate(2012, 11, 12);
		System.out.println(client);

		System.out.println("Time in Asia/Shanghai: "
				+ client.getZonedDateTime("Asia/Shanghai"));
	}
}