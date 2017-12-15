package apache_log4j;

import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;

public class TestLog4j 
{
	public static final String propFile= "./src/apache_log4j/log4j.properties";//OK
	public static final String xmlFile=  "./src/apache_log4j/log4j.xml";//OK
	public static final String log_home="C:/tmp";
	public static void log4jPropFile()//OK
	{
		Logger logger = Logger.getLogger(TestLog4j.class);  
		System.setProperty("log_home",log_home);
		org.apache.log4j.PropertyConfigurator.configure(propFile);
		logger.debug("Prop  File   ");
	}
	public static void log4jXMLFile()//OK
	{
		Logger logger = Logger.getLogger(TestLog4j.class);  
		System.setProperty("log_home",log_home);
		org.apache.log4j.xml.DOMConfigurator.configure(xmlFile);
		logger.debug("XML File   ");
	}
	public static void log4jCode()//OK
	{
		try {
		PatternLayout layout = new PatternLayout();
		layout.setConversionPattern("[%d{yyyy-MM-dd HH:mm:ss}][%5p][%t][%l] - [%m]%n");
		DailyRollingFileAppender myAppender = new DailyRollingFileAppender(layout, "c:/log_code.txt", "'.'yyyy-MM-dd");
		
		ConsoleAppender consoleAppender;
		
		Logger rootLog=Logger.getRootLogger();
		rootLog.setLevel(Level.DEBUG);
		
		Logger log = Logger.getLogger(TestLog4j.class);
		log.setAdditivity(false);//���̳�
		
		log.setLevel(Level.DEBUG);
		
		 log.addAppender(myAppender);
		 log.info("INFO  Code");

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/*
	 ��X��: X��Ϣ���ʱ����룻
   %p: �����־��Ϣ���ȼ�����DEBUG��INFO��WARN��ERROR��FATAL,
   %d: �����־ʱ�������ڻ�ʱ�䣬Ĭ�ϸ�ʽΪISO8601��Ҳ���������ָ����ʽ�����磺%d{yyy MMM dd HH:mm:ss,SSS}��������ƣ�2002��10��18�� 22��10��28��921
   %r: �����Ӧ�������������log��Ϣ�ķѵĺ�����
   %c: �����־��Ϣ��������Ŀ��ͨ�������������ȫ��
   %t: �����������־�¼����߳���
   %l: �����־�¼��ķ���λ�ã��൱��%C.%M(%F:%L)�����,������Ŀ�����������̣߳��Լ��ڴ����е�������������Testlog4.main(TestLog4.java:10)
   %x: ����͵�ǰ�߳��������NDC(Ƕ����ϻ���),�����õ���java servlets�����Ķ�ͻ����̵߳�Ӧ���С�
   %%: ���һ��"%"�ַ�
   %F: �����־��Ϣ����ʱ���ڵ��ļ�����
   %L: ��������е��к�
   %m: ���������ָ������Ϣ,��������־������Ϣ
   %n: ���һ���س����з���Windowsƽ̨Ϊ"\r\n"��Unixƽ̨Ϊ"\n"�����־��Ϣ����
������%��ģʽ�ַ�֮��������η�����������С��ȡ�����ȡ����ı��Ķ��뷽ʽ���磺
	 1)%20c��ָ�����category�����ƣ���С�Ŀ����20�����category������С��20�Ļ���Ĭ�ϵ�������Ҷ��롣
	 2)%-20c:ָ�����category�����ƣ���С�Ŀ����20�����category������С��20�Ļ���"-"��ָ������롣
	 3)%.30c:ָ�����category�����ƣ����Ŀ����30�����category�����ƴ���30�Ļ����ͻὫ��߶�����ַ��ص�����С��30�Ļ�Ҳ�����пո�
	 4)%20.30c:���category������С��20�Ͳ��ո񣬲����Ҷ��룬��������Ƴ���30�ַ����ʹ���߽�Զ�������ַ��ص���

	 */
	public static void main(String[] args)
	{
		//log4jCode();
		log4jXMLFile();
		//log4jPropFile();
		
	}

}
