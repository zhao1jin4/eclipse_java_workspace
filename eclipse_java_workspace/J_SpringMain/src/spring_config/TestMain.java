package spring_config;

import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class TestMain {
	public static void testSame()
	{
		//测试是同一对象
		ApplicationContext context=new ClassPathXmlApplicationContext("spring_config/applicationContext.xml");
		User u1=(User)context.getBean("user1");
		User u2=(User)context.getBean("user1");
		System.out.println(u1==u2);
		System.out.println(u1.getSets());
		System.out.println(u1.getLists());
		System.out.println(u1.getMaps());
		System.out.println(u1.getProps());
		System.out.println(u1.getArrays());
		

		//基本 
		Object driverClass=context.getBean("driverClass");
		System.out.println(driverClass.getClass().getName());
		
		Object driverInstance=context.getBean("driverInstance");
		System.out.println(driverInstance.getClass().getName());
		

	}
	public static void testConfig()
	{
		//父子BeanFactory
		DefaultListableBeanFactory   parent = new DefaultListableBeanFactory(); 
		new XmlBeanDefinitionReader(parent).loadBeanDefinitions(new ClassPathResource("spring_config/parent.xml")); 
		  
		DefaultListableBeanFactory   child = new DefaultListableBeanFactory(parent); 
		new XmlBeanDefinitionReader(child).loadBeanDefinitions(new ClassPathResource("spring_config/child.xml")); 
		  
		Parent  p =(Parent)child.getBean("beanParent");
		System.out.println("得到配置的名字是:"+p.getName());
		System.out.println("得到配置的年龄是:"+p.getUser().getAge());
		User u=(User)child.getBean("beanUser");
		System.out.println("得到parent配置的是:"+u.getHome().getHomeaddr());
		
		MyLookupBean lookup1=(MyLookupBean)child.getBean("myLookup");
		lookup1.doWithAnotherBean();
		MyLookupBean lookup2=(MyLookupBean)child.getBean("myLookup");
		lookup2.doWithAnotherBean();
		
		MyValueCalculator calc=(MyValueCalculator)child.getBean("myValueCalculater");
		System.out.println("替换方法返回的是"+calc.computeValue("abc"));
		
		System.out.println("读到的年龄是:"+child.getBean("readAge"));
		System.out.println("parent 读到的年龄是:"+child.getBean("readAge2"));
		System.out.println("user.age读到的年龄是:"+child.getBean("beanParent.user.age"));
		
		System.out.println("staticField读到的字段值是:"+child.getBean("readFeild"));
		System.out.println(".staticField读到的字段值是:"+child.getBean("java.sql.Connection.TRANSACTION_SERIALIZABLE"));
		
		System.out.println("读到静态方法返回值是:"+child.getBean("calender"));
		System.out.println("读到静态方法2返回值是:"+child.getBean("javaVersion"));
		
		DefaultPointcutAdvisor x;
		DynamicMethodMatcherPointcut y;
	}
	public static void main(String[] args) 
	{
		testSame();
		testConfig();
	}
}
