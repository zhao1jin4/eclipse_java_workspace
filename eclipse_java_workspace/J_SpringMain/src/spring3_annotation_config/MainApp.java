package spring3_annotation_config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class MainApp  
{
	public static void useXML()
	{
		AbstractApplicationContext context=new ClassPathXmlApplicationContext("spring3_annotation_config/config_beans.xml");
		
//		Object service=(Object)context.getBean("baseService");
		Object service1=(Object)context.getBean("baseService1");
		Object service1_2=(Object)context.getBean("baseService1");
		System.out.println(service1==service1_2);
		
		Object dao=(Object)context.getBean("baseDao");
		System.out.println(dao);
	
		
		UserService userService1=(UserService)context.getBean("userServcie");
		userService1.userDao.save("жа");
		context.close();
	}
	public static void useAnnotation()
	{
		 ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		 UserService service1= context.getBean(UserService.class);
		 UserService service2= context.getBean(UserService.class);
			
		 System.out.println(service1==service2);
			
		 UserDAO dao= context.getBean(UserDAO.class);
		 
		System.out.println(dao.getTest());
		
		 
		service2.userDao.save("жа");
		//context.close();
	}
	
	public static void main(String[] args) 
	{
		//System.out.println(System.getProperties());
		//useXML();
		useAnnotation();
	}
}
