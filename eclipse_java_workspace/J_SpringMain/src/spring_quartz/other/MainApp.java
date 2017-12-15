package spring_quartz.other;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
	public static void threadPool() 
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring_quartz/other/concurrent_benas.xml");
		TaskExecutorExample example=context.getBean("taskExecutorExample",TaskExecutorExample.class);
		example.printMessages();
	}
	public static void planTask() 
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring_quartz/other/scheduling_benas.xml");
		MySomeBus bus=context.getBean("mySomeBus",MySomeBus.class);//为异步调用
		bus.asyncReturnSomething();
		System.out.println("programe end.");
		try {
			Thread.sleep(90000*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) 
	{
		//threadPool();
		planTask();
	}

}
