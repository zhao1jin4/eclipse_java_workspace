package spring_aop_proxy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMain {

	public static void main(String[] args) {

		ApplicationContext context=new ClassPathXmlApplicationContext("spring_aop_proxy/aop_proxy-beans.xml");
		
		Computer com=(Computer)context.getBean("proxy");
		com.computerInfo();
		com.showMessage();
		

 		Monitor mo=(Monitor)context.getBean("proxy");
		mo.display(); 

		
		 
	}

}
