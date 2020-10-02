package myservlet.spring;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import spring_jsp.annotation.form.Account;
  
  
@Controller //这个可有可无, No WebApplicationContext found: no ContextLoaderListener registered?
@Scope("prototype")
@WebServlet(urlPatterns="/servletWithSpring.ser")
public class ServletWithSpring extends HttpServlet {
  
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private Validator validator;  // @Autowired不行？？？？ 
	
	public void init(ServletConfig config) throws ServletException
	{
		//要打开  ContextLoaderListener  才行
		//SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}
 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	 
		PrintWriter out = response.getWriter();
		Account account=new Account(); 
		Set<ConstraintViolation<Account>> violations = null;
//		Set<ConstraintViolation<Account>> violations = validator.validate(account); 
		out.println("ServletWithSpring validate:"+violations);
         
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
 
}