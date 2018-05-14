package jdk8_new.date;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public interface TimeClient2 {
	void setTime(int hour, int minute, int second);

	void setDate(int day, int month, int year);

	void setDateAndTime(int day, int month, int year, int hour, int minute,
			int second);

	LocalDateTime getLocalDateTime();

	static ZoneId getZoneId(String zoneString) { 
		System.out.println("在接口TimeClient中");
		try {
			return ZoneId.of(zoneString);
		} catch (DateTimeException e) {
			System.err.println("Invalid time zone: " + zoneString
					+ "; using default time zone instead.");
			return ZoneId.systemDefault(); //jdk8新类
		}
	}
	
	//接口中有方法实现,方法前加default或static
	//default方法 能够添加新的功能到已经存在的接口，确保与采用老版本这些接口编写的代码的二进制兼容性
	//比抽象类好处可以多重继承,一个类继承两个接口时,这个两个接口中如有相同的default方法,子类必须重写
	//接口中定义的变量默认是public static final 型，且必须给其初值，所以实现类中不能重新定义，也不能改变其值;抽象类中的变量默认是 friendly 型，其值可以在子类中重新定义，也可以重新赋值
	 default  ZonedDateTime getZonedDateTime(String zoneString) {
		return ZonedDateTime.of(getLocalDateTime(), getZoneId(zoneString));
	}
}