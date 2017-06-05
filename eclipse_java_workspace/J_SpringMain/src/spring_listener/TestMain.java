package spring_listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMain {

	public static void main(String[] args) {
	ApplicationContext context=new ClassPathXmlApplicationContext("spring_listener/listener_beans.xml");
	Publisher pulisher=(Publisher)context.getBean("publisher");
	pulisher.publish();

	}

}
