package slf4j_log4j_commonsLogging;
import java.io.FileNotFoundException;

import org.apache.log4j.Level;
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
   
 public class TestSLF4J
 {  
	 final Logger logger = LoggerFactory.getLogger(TestSLF4J.class);  
	 Integer t;  
	 Integer oldT;  
	   
	 public void setTemperature(Integer temperature) 
	 {  
	    oldT = t;  
	    t = temperature;  
	     
	    Object[] objs = {new java.util.Date(), oldT, t};  
	   // logger.info("Today is {}, Temperature set to {}. Old temperature was {}.", objs);  
	    
	    logger.info("Today is {}, Temperature set to {}.", new Object[]{new java.util.Date(), 20});  
	    if (temperature.intValue() > 50)
	    {  
	     logger.warn("Temperature({}) has risen above 50 degrees.", t);  
	    }  
	 }  
	 void execeptionLog()
	 {
		 logger.error("文件找不到",new FileNotFoundException("/test.txt"));//传入Throwable,会在日志有异常堆栈
	 }
	 public static void main(String[] args) 
	 {  
		TestSLF4J wombat = new TestSLF4J();  
	    wombat.setTemperature(10);  
	    wombat.setTemperature(60); 
	    wombat.execeptionLog();
	 }  
 }  