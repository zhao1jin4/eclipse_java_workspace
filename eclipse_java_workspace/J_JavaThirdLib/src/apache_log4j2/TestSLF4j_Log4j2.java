package apache_log4j2;

import java.io.File;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class TestSLF4j_Log4j2 
{
	public static void main(final String... args) throws Exception
	{
		//����ָ�������ļ�λ�� ��ʽһ
		String configFile=new File("./src/apache_log4j2/log4j2.xml").getAbsolutePath();
		System.setProperty("log4j.configurationFile", "file://"+configFile);
	
		//����ָ�������ļ�λ�� ��ʽ��
//		ConfigurationSource source = new ConfigurationSource(TestLog4j2.class.getResourceAsStream("/apache_log4j2/log4j2.xml"));  
//		Configurator.initialize(null, source);
		//�� xi:include �����Ҳ���,����Ϊ<xi:include href="src/apache_log4j2/log4j2-xinclude-props.xml" />
       
		Logger logger = LoggerFactory .getLogger(TestSLF4j_Log4j2.class);
		for(int i=0;i<2;i++)
		{
			logger.trace("Entering application.");
			logger.error("Didn't do it.");
			logger.trace("Exiting application.");
		}
	
	}
}