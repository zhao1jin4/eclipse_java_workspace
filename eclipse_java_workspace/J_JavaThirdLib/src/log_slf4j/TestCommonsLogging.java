package log_slf4j;

import java.io.FileNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestCommonsLogging 
{
	
	static void execeptionLog(Log logger)
	 {
		 logger.error("�ļ��Ҳ���",new FileNotFoundException("/test.txt"));//����Throwable,������־���쳣��ջ
	 }
	public static void main(String[] args) 
	{
		Log log=LogFactory.getLog(TestCommonsLogging.class);
		log.info("������Ϣ");
		execeptionLog(log);
	}

}
