package org.zhaojin;

import junit.framework.TestCase;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.zhaojin.propertyEditor.User;


public class TestManager extends TestCase
{
	ClassPathXmlApplicationContext context;
	protected void setUp() throws Exception {
		super.setUp();
		 context=	new ClassPathXmlApplicationContext("org/zhaojin/applicationContext.xml");
	}
	protected void tearDown() throws Exception {
		super.tearDown();
		context.close();
	}

	public void testIP()
	{
		UserManager um=(UserManager)context.getBean("userManager");
		this.assertEquals(um.login("lisi", "192.168.100.100"),false);
	}
	
	public void testProcess()
	{
		ConfigurableBeanFactory factory=new XmlBeanFactory(new ClassPathResource("org/zhaojin/applicationContext.xml"));
		MyProcessor processor=new MyProcessor();
		factory.addBeanPostProcessor(processor);
		UserManager manager=(UserManager)factory.getBean("userManager");
		System.out.println("========"+manager.getIp());
	}
	
 	public void testBeanFactory()
	{
 		org.zhaojin.beanFactoryPostProcessor.User u=(org.zhaojin.beanFactoryPostProcessor.User)context.getBean("user");
		System.out.println("THE USE IS "+u.getId()+";"+u.getPassword());
		
	} 
 	public void testPropertyEditor()
	{
		User u=(User)context.getBean("userEditor");
		System.out.println(u.getName().getFirstname()+":"+u.getName().getMiddlename()+":"+u.getName().getLastname()+":"+u.getAge());

	}
	
	public void testFactoryBean()
	{
		User u=(User)context.getBean("myFactoryBean");// FactoryBean 用来产生对象的 可以强转
		System.out.println(u.getName().getFirstname()+":"+u.getName().getMiddlename()+":"+u.getName().getLastname()+":"+u.getAge());
		
		Object o=context.getBean("&myFactoryBean");//& ,如想得到FactoryBean本身要在getBean("&beanName");就不会调用getObject()
		System.out.println(o.getClass().getName());
	}
}
