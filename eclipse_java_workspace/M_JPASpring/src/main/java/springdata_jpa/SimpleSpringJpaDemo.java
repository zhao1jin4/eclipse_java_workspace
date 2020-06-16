package springdata_jpa;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SimpleSpringJpaDemo{ 
	public static void main(String[] args){ 
		org.hibernate.jpa.HibernatePersistenceProvider x;
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("springdata_jpa/jpa.xml"); 
		UserService userService = ctx.getBean("userService", UserService.class); 
		//.NoSuchBeanDefinitionException: No bean named 'userService' available
		userService.createNewAccount("ZhangJianPing", "123456", 1); 
	} 
}