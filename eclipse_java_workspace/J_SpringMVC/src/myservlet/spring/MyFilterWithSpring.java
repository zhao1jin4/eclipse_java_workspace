package myservlet.spring;

import java.io.IOException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring_jsp.annotation.form.Account;

//web.xml有配置
@Component("myFilterWithSpring")
public class MyFilterWithSpring implements Filter
{
	@Autowired
	private Validator validator; //Filter类中就可以注入Spring容器中的类
	//WebApplicationContextUtils 不如这种方便
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fileChain)
			throws IOException, ServletException {
		System.out.println("MyFilterWithSpring before");
		Account account=new Account();
		Set<ConstraintViolation<Account>> violations = validator.validate(account); 

		System.out.println("MyFilterWithSpring validate:"+violations);
		fileChain.doFilter(request, response);
		System.out.println("MyFilterWithSpring after");
		
	}

}
