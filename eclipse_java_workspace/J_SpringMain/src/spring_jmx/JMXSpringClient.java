package spring_jmx;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JMXSpringClient 
{
	public static void main(String[] args) throws Exception
	{
		ApplicationContext context=new ClassPathXmlApplicationContext("spring_jmx/jmx_client_beans.xml");
		StandardMBean std=(StandardMBean)context.getBean("proxy");
		System.out.println("standard :"+std.getLogLevel()); 
		
		
	}
}
