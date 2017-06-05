package spring_aopaliance;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring_aopaliance.ILogin;

public class MainApp
{
	public static void main(String args[])
	{
		ApplicationContext context=new ClassPathXmlApplicationContext("spring_aop_advisor/advisor_beans.xml");
		ILogin l=(ILogin)context.getBean("proxy");
		l.login("zhang", "123");
	}
}
