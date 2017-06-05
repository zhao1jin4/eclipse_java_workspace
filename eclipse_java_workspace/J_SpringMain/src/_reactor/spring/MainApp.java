package _reactor.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
	reactor.spring.factory.CreateOrReuseFactoryBean x;
	
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("_reactor/spring/reactor_beans.xml");
		TestService testService =(TestService)context.getBean("testService");
		testService.fireEvent("�ҵ��¼�����");  //ûЧ�� ?????????
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
