package spring_aop_aspectj_anno;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAspectJMain {
	
	public static void main(String[] args) 
	{
 
		anno( ) ;//error
	}
	public static void anno( ) 
	{
		ApplicationContext context=new AnnotationConfigApplicationContext(Config.class);
//		ApplicationContext context=new ClassPathXmlApplicationContext("spring_aop_aspectj_anno/annotation_aop_beans2.xml");
		AnnoService annoService= context.getBean(AnnoService.class);
		boolean res=annoService.validateUser("lisi");
		System.out.println("res="+res);
	}

}

@Configuration
@EnableAspectJAutoProxy
class Config
{
	@Bean
	public AnnoAspect newAspect()
	{
		return new AnnoAspect();
	}
	@Bean
	public AnnoService newService()
	{
		return new AnnoService();
	}
}