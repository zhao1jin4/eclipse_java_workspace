package apache_log4j2;

import java.io.File;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
 
public class TestLog4j2 
{
	public static void printAllSystemProps()
	{
		Properties prop=System.getProperties();
		System.out.println(prop);
	}
	public static void main(final String... args) throws Exception
	{
//		StructuredDataMessage sd=null;
//		sd.getId();
//		sd.getType();
//		org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy y;
		
		
//		printAllSystemProps();
//		有用的变量   user.dir , user.home,   java.io.tmpdir, os.name ,  file.encoding ,java.runtime.version

		//代码指定配置文件位置 方式一
		String configFile=new File("./src/apache_log4j2/log4j2.xml").getAbsolutePath();
		System.setProperty("log4j.configurationFile", "file://"+configFile);
		
		//代码指定配置文件位置 方式二
//		ConfigurationSource source = new ConfigurationSource(TestLog4j2.class.getResourceAsStream("/apache_log4j2/log4j2.xml"));  
//		Configurator.initialize(null, source);
		//但 xi:include 容易找不到,必须为<xi:include href="src/apache_log4j2/log4j2-xinclude-props.xml" />
       
		Logger logger = LogManager.getLogger(TestLog4j2.class);
	 
		//%d 默认格式 同    %d{yyyy-MM-dd HH:mm:ss,SSS}
		//%t thread
		// %c{1.}  或  %logger{1.}  包名只用一个字母
		// %p 或 %level{WARN=W, DEBUG=D, ERROR=E, TRACE=T, INFO=I}    
		for(int i=0;i<2;i++)
		{
			logger.trace("Entering application.");
			logger.error("Didn't do it.");
			logger.trace("Exiting application.");
		}
	
	}
}