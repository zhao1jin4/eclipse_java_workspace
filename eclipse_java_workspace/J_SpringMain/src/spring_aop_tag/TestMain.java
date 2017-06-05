package spring_aop_tag;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMain {

	public static void main(String[] args) 
	{

		ApplicationContext context=new ClassPathXmlApplicationContext("spring_aop_tag/aop_tag_beans.xml");
		ABCService service=(ABCService)context.getBean("abcService");
		try {
			//service.businessMethod();
			String abc=service.businessMethod("����");
		} catch (Exception e) {
			System.out.println("����������catch");
			e.printStackTrace();
		}
	}
}
