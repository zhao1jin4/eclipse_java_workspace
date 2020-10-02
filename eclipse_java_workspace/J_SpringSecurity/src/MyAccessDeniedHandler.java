import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 *<security:access-denied-handler ref=""/>
 * 
 * 已经没有  access-denied-page="/auth/denied.mvc"  了
 * @author zhaojin
 * 
 *
 */
public class MyAccessDeniedHandler implements  AccessDeniedHandler {

	 
	@Override
	public void handle(HttpServletRequest req, HttpServletResponse resp, AccessDeniedException excp)
			throws IOException, ServletException {
		 
		System.out.println("MyAccessDeniedHandler："+excp);
//		req.getRequestDispatcher("auth/denied.mvc").forward(req, resp); 
		resp.sendRedirect("auth/denied.mvc");
	}

}
