package logback;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.status.OnConsoleStatusListener;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.util.StatusPrinter;

public class LogBackTest 
{
	public static void main(String[] args) throws Exception 
	{
		
		// activemq-all-5.10.0.jar   ���� ��  slf4j-log4j12-1.7.5.jar һ����ʵ��  Ҫȥ��
		//���� slf4j-api.jar slf4j-api-1.7.6.jar , logback-core-1.1.2.jar  ,logback-classic-1.1.2.jar  ֱ��ʵ����SLF4J API
		//�����<if condition ,Ҫ Janino �� janino-2.7.5.jar ���� commons-compiler-2.7.5.jar
		//��� Spring ʹ�õ� commons-logging �� jcl-over-slf4j-1.7.6.jar
		
		//java -Dlogback.configurationFile=/path/to/config.xml  Ĭ��classpath�µ�logback.xml
		String currentDir=new File(".").getAbsolutePath();// X:\eclipse_java_workspace\J_JavaThirdLib\
		System.setProperty("logback.configurationFile",currentDir+"/src/logback/logback.xml");//Ҫʹ�þ���·��
		
		Logger logger = LoggerFactory .getLogger("MyAppName.biz");
		Logger logger2 = LoggerFactory .getLogger("MyAppName.biz");
		logger.info("logger==logger2 : {}", logger==logger2 );//��ͬ�Ķ���
		
		Object date = new java.util.Date(); 
		logger.debug("today is: {} ", date);
		logger.info("os.name = {}", System.getProperty("os.name")); //Windows 7
		Logger daoLogger = LoggerFactory .getLogger("MyAppName.dao");
		daoLogger.info("the SQL is: insert into myTable(id,name)values(?,?)");
		
		Logger serviceOutLogger = LoggerFactory .getLogger("MyAppName.serviceOut");
		serviceOutLogger.info("invoke the risk check system API checkBlackList Success,elase time 200s");
	
		
		//�����logback��ص�����ʱ,MyApp2.java
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//		StatusPrinter.print(lc);//<configuration debug="true">ʱ�ɴ�ӡ��ϸ��Ϣ
		
		//����ָ�������ļ�
		JoranConfigurator configurator = new JoranConfigurator(); 
		configurator.setContext(lc); 
		lc.reset();
		configurator.doConfigure(currentDir+"/src/logback/logback.xml");  
		StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
		
	
		//������״̬��Ϣ������Ӧ,����Щlogback�������֮�����Ϣ
		//Web Ӧ�� ch.qos.logback.classic.ViewStatusMessagesServlet
		StatusManager statusManager = lc.getStatusManager(); 
		OnConsoleStatusListener onConsoleListener = new OnConsoleStatusListener();
		statusManager.add(onConsoleListener);
		//<statusListener class="ch.qos.logback.core.status.OnConsoleStatusListener" />���߼�ϵͳ���� logback.statusListenerClass=
		
		
		springConsole();//Spring OK
	}
	public static void springConsole() throws Exception 
	{
		ClassPathXmlApplicationContext ctx =new ClassPathXmlApplicationContext("logback/applicationContext.xml");
		ctx.close();
	}
}
