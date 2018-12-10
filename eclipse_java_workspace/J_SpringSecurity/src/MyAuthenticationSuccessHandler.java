import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
   
	@Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) 
    {
		System.out.println("AuthenticationSuccessHandler onAuthenticationSuccess invoked ");
        try {
			response.sendRedirect("main/common.mvc");  //会替代  default-target-url="/main/common1.mvc"
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
 
}