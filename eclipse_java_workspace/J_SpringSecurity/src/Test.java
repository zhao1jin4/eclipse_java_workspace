import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;


public class Test {

	public static void main(String[] args) 
	{
		org.springframework.web.context.ContextLoaderListener x=null;
		 org.springframework.web.filter.DelegatingFilterProxy y=null;
		 org.springframework.jdbc.core.support.JdbcDaoSupport z=null;
		 org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl aa=null;
		 org.springframework.security.web.session.HttpSessionEventPublisher bb=null;
		 
//		 OpenIDAuthenticationToken token = (OpenIDAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();//代码中得到用户信息
//		 List<OpenIDAttribute> attributes = token.getAttributes();
		 
	}
	
	public String a;
	
	@PreAuthorize("isAnonymous()")
	@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
	public void test()
	{
		
	}

}
