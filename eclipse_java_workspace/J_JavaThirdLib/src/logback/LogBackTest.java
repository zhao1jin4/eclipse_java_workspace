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
	//SizeAndTimeBasedFNATP is deprecated. Use SizeAndTimeBasedRollingPolicy instead
	public static void main(String[] args) throws Exception 
	{
		//依赖 slf4j-api-x.jar , logback-core-x.jar  ,logback-classic-x.jar  直接实现了SLF4J API
		
		//如果用<if condition ,要 Janino 库 janino-2.7.5.jar 依赖 commons-compiler-2.7.5.jar
		//替代 Spring 使用的 commons-logging 加 jcl-over-slf4j-1.7.6.jar
		
		//java -Dlogback.configurationFile=/path/to/config.xml  默认classpath下的logback.xml
		String currentDir=new File(".").getAbsolutePath();// X:\eclipse_java_workspace\J_JavaThirdLib\
		System.setProperty("logback.configurationFile",currentDir+"/src/logback/logback.xml");//要使用绝对路径
		
		Logger logger = LoggerFactory .getLogger("MyAppName.biz");
		Logger logger2 = LoggerFactory .getLogger("MyAppName.biz");
		logger.info("logger==logger2 : {}", logger==logger2 );//相同的对象
		logger.error("ERROR-TEST" ); 
		Object date = new java.util.Date(); 
		logger.debug("today is: {} ", date);
		logger.info("os.name 系统 = {}", System.getProperty("os.name")); //Windows 7
		Logger daoLogger = LoggerFactory .getLogger("MyAppName.dao");
		daoLogger.info("the SQL is: insert into myTable(id,name)values(?,?)");
		
		Logger serviceOutLogger = LoggerFactory .getLogger("MyAppName.serviceOut");
		serviceOutLogger.info("invoke the risk check system API checkBlackList Success,elase time 200s");
	
		
		//诊断与logback相关的问题时,MyApp2.java
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//		StatusPrinter.print(lc);//<configuration debug="true">时可打印详细信息
		
		//程序指定配置文件
		JoranConfigurator configurator = new JoranConfigurator(); 
		configurator.setContext(lc); 
		lc.reset();
		configurator.doConfigure(currentDir+"/src/logback/logback.xml");  
		StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
		
	
		//立即对状态消息作出响应,对那些logback配置完成之后的消息
		//Web 应用 ch.qos.logback.classic.ViewStatusMessagesServlet
		StatusManager statusManager = lc.getStatusManager(); 
		OnConsoleStatusListener onConsoleListener = new OnConsoleStatusListener();
		statusManager.add(onConsoleListener);
		//<statusListener class="ch.qos.logback.core.status.OnConsoleStatusListener" />或者加系统属性 logback.statusListenerClass=
		
		Logger LOG =LoggerFactory.getLogger(LogBackTest.class);
		LOG.info("测试INFO日志在LogBackTest类中");
		springConsole();//Spring OK
	}
	public static void springConsole() throws Exception 
	{
		ClassPathXmlApplicationContext ctx =new ClassPathXmlApplicationContext("logback/applicationContext.xml");
		ctx.close();
	}
}
