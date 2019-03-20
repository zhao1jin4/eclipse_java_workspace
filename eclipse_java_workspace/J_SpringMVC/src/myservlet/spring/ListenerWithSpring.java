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
//ֻ��������web.xml ��  ContextLoaderListener ���غ�  @AutowiredҲ���У�������
public class ListenerWithSpring implements ServletContextListener {
    
	@Autowired
	private Validator validator; //@Autowired���У�������
	
   @Override
   public void contextInitialized(ServletContextEvent sce) { 
	   //�粻�� @WebListener��Ų���web.xml���� ,  ����ִ�е�(����component-scan,@Component),  
	   WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
	  
	   Account account=new Account();
	  Set<ConstraintViolation<Account>> violations = validator.validate(account); 

		System.out.println("ListenerWithSpring validate:"+violations);

   }

   @Override
   public void contextDestroyed(ServletContextEvent sce) {
   }
 
}