package spring_aop_proxy2;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp2
{

	public static void main(String[] args)
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_aop_proxy2/aop_proxy-beans2.xml");
		BusinessLogic logic = (BusinessLogic)ctx.getBean("businessLogic");
		logic.bar();
		logic.foo("lisi");
	}

}
