import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 外部系统验证
 * <security:httpe entry-point-ref=""   >  
 * @author zhaojin
 *
 */
public class MyAuthenticationEntryPoint implements  AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException arg2)
			throws IOException, ServletException {
		 
		//特殊代码处理，如url是从另一系统取得
		System.out.println("AuthenticationEntryPoint  entered:");
		resp.sendRedirect("http://www.baidu.com"); //验证成功后另一系统会回调 
		// http://127.0.0.1:8080/J_SpringSecurity/auth/loginNotify.mvc (可手工模拟)
	}

}
