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
		System.out.println("�ڽӿ�TimeClient��");
		try {
			return ZoneId.of(zoneString);
		} catch (DateTimeException e) {
			System.err.println("Invalid time zone: " + zoneString
					+ "; using default time zone instead.");
			return ZoneId.systemDefault(); //jdk8����
		}
	}
	
	//�ӿ����з���ʵ��,����ǰ��default��static
	//default���� �ܹ�����µĹ��ܵ��Ѿ����ڵĽӿڣ�ȷ��������ϰ汾��Щ�ӿڱ�д�Ĵ���Ķ����Ƽ�����
	//�ȳ�����ô����Զ��ؼ̳�,һ����̳������ӿ�ʱ,��������ӿ���������ͬ��default����,���������д
	//�ӿ��ж���ı���Ĭ����public static final �ͣ��ұ�������ֵ������ʵ�����в������¶��壬Ҳ���ܸı���ֵ;�������еı���Ĭ���� friendly �ͣ���ֵ���������������¶��壬Ҳ�������¸�ֵ
	 default  ZonedDateTime getZonedDateTime(String zoneString) {
		return ZonedDateTime.of(getLocalDateTime(), getZoneId(zoneString));
	}
}