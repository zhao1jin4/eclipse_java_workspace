package spring_aop_introduction;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class TestIntroductionMain
{

	public static void main(String[] args)
	{
//		Resource res=new ClassPathResource("spring_introduction/introduction_beans.xml");
//		BeanFactory factory=new XmlBeanFactory(res);
//		IService service=(IService)factory.getBean("proxy"); 
		
		
		//�Զ�����Ҫʹ��ApplicationContext,�Զ�����ǿת�ӿ�,���ߵ��÷������ڵĽӿ�,��Advisor
		ApplicationContext context=new ClassPathXmlApplicationContext("spring_aop_introduction/introduction_beans.xml");
		IService service=(IService)context.getBean("serviceTarget");
		
		service.write("hello spring");
		
		
		IAnother another=(IAnother)service;
		another.printHello();
		
		ILockable lock=(ILockable)service;
		lock.lock();
		service.write("next");

	}
}
