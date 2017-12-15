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

	private LocalDateTime dateAndTime; //jdk8����

	public SimpleTimeClient() {
		dateAndTime = LocalDateTime.now();
	}

	public void setTime(int hour, int minute, int second) {
		LocalDate currentDate = LocalDate.from(dateAndTime); 
		LocalTime timeToSet = LocalTime.of(hour, minute, second);//of��ʾ�޸�
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

	
	//ֻ����дdefault��(���Բ���д),������дstatic ��
	//һ����̳������ӿ�ʱ,��������ӿ���������ͬ��default����,���������д
	@Override
	public ZonedDateTime getZonedDateTime(String zoneString) {
		System.out.println("default����SimpleTimeClient��");
		return TimeClient.super.getZonedDateTime(zoneString);
		//return null;
	}
	
	//ͬ������������д,ʵ������
	public 	static ZoneId getZoneId(String zoneString) { 
		System.out.println(" static ��SimpleTimeClient����");
		return ZoneId.systemDefault();
	}
	
	public static void main(String... args) {
		SimpleTimeClient client = new SimpleTimeClient();
		// ��ʾ��ǰ����ʱ��
		System.out.println(client.toString());
		// ��������
		client.setTime(11, 12, 22);
		System.out.println(client);
		// ����ʱ��
		client.setDate(2012, 11, 12);
		System.out.println(client);

		System.out.println("Time in Asia/Shanghai: "
				+ client.getZonedDateTime("Asia/Shanghai"));
	}
}