package log_slf4j;

import java.io.FileNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestCommonsLogging 
{
	
	static void execeptionLog(Log logger)
	 {
		 logger.error("文件找不到",new FileNotFoundException("/test.txt"));//传入Throwable,会在日志有异常堆栈
	 }
	public static void main(String[] args) 
	{
		Log log=LogFactory.getLog(TestCommonsLogging.class);
		log.info("测试信息");
		execeptionLog(log);
	}

}
