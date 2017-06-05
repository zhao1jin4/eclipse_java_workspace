package slf4j_log4j_commonsLogging;

import java.io.FileNotFoundException;
import java.net.URL;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class TestLog4j {
	static void execeptionLog(Logger logger)
	 {
		 logger.error("�ļ��Ҳ���",new FileNotFoundException("/test.txt"));//����Throwable,������־���쳣��ջ
	 }
	
	private void initLog4j()
	{
		System.setProperty("log_home","c:/log");
		URL url=this.getClass().getResource("/slf4j_log4j_commonsLogging/log4j.xml");
		DOMConfigurator.configure(url);
		
		Logger log=Logger.getLogger(TestLog4j.class);
		log.info("XML������Ϣ");
	}
	public static void main(String[] args) 
	{
		Logger rootLog=Logger.getRootLogger();
		rootLog.setLevel(Level.WARN);
		
		Logger log=Logger.getLogger(TestLog4j.class);
		log.setLevel(Level.DEBUG);
		log.info("������Ϣ");
		execeptionLog(log);
		
		new TestLog4j().initLog4j();
	}

}
