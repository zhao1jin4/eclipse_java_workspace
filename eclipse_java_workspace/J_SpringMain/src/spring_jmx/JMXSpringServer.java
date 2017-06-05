package spring_jmx;

import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.NotificationFilter;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JMXSpringServer 
{
	
	public static void other()
	{
		NotificationFilter filter;
		//(MyMBean)MBeanServerInvocationHandler.newProxyInstance(arg0, arg1, arg2, arg3);
		//类似于JMX.newMBeanProxy();
		MBeanServerFactory.createMBeanServer("test");
	}
	public static void main(String[] args) throws Exception
	{
		ApplicationContext context=new ClassPathXmlApplicationContext("spring_jmx/jmx_server_beans.xml");
		MBeanServer mbserver=(MBeanServer)context.getBean("mbeanServer");
		
//		MyBean myBean=(MyBean)context.getBean("myBean");
//		System.out.println("MyBean LogLevel:"+myBean.getLogLevel());
//		mbserver.setAttribute(ObjectName.getInstance("mydomain:myType=MyBean"), new Attribute("LogLevel","DEBUG"));//是大写的LogLevel
//		System.out.println("MyBean LogLevel:"+myBean.getLogLevel());
		
		Standard std=(Standard)context.getBean("mydomain:myType=Standard");
		System.out.println("Standard MBean PageSize:"+std.getPageSize()); 
		
		//---Annotation
		AnnotationTestBean test=(AnnotationTestBean)context.getBean("testBean");
		//mbserver.setAttribute(ObjectName.getInstance("bean:name=testBean4"), new Attribute("Age","23"));//readonly ,不能修改
		mbserver.setAttribute(ObjectName.getInstance("bean:name=testBean4"), new Attribute("Name","小王"));
		System.out.println("testBean Name:"+test.getName());
		
//		//---失败??????
//		YourBean your=(YourBean)context.getBean("yourBean");
//		mbserver.setAttribute(ObjectName.getInstance("mydomain:myType=YourBean"), new Attribute("LogLevel","NOTICE"));
//		System.out.println("YourBean LogLevel:"+your.getLogLevel()); 
//		
//		HerBean her=(HerBean)context.getBean("herBean");
//		mbserver.setAttribute(ObjectName.getInstance("mydomain:myType=HerBean"), new Attribute("LogLevel","ERROR"));
//		System.out.println("HerBean LogLevel:"+her.getLogLevel()); 
		
		
		System.out.println("done ."); 
		
		
	}
}
