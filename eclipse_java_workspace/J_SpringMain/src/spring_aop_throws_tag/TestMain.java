package spring_aop_throws_tag;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMain
{
	public static void main(String args[])
	{
		ApplicationContext context=new ClassPathXmlApplicationContext("spring_aop_throws_tag/aop_throws_beans.xml");
		ILogin login=(ILogin)context.getBean("proxy");
		
		login.login("zhangsan", "123");
		login.login("lisi", "333");
		//System.out.print("testAop Mehtod");

	}
}

