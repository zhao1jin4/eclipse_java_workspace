package myservlet.spring;
 
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import spring_jsp.annotation.form.Account;
  
  
//@Component("listenerWithSpring")
//@WebListener 
//只能配置在web.xml 在  ContextLoaderListener 加载后  @Autowired也不行？？？？
public class ListenerWithSpring implements ServletContextListener {
    
	@Autowired
	private Validator validator; //@Autowired不行？？？？
	
   @Override
   public void contextInitialized(ServletContextEvent sce) { 
	   //如不加 @WebListener或才不在web.xml配置 ,  不会执行的(加了component-scan,@Component),  
	   WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
	  
	   Account account=new Account();
	  Set<ConstraintViolation<Account>> violations = validator.validate(account); 

		System.out.println("ListenerWithSpring validate:"+violations);

   }

   @Override
   public void contextDestroyed(ServletContextEvent sce) {
   }
 
}