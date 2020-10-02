import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;
  
/**
 * intercept-url  pattern= 编程式，但不能匹配  ROLE
 * @author zhaojin
 *
 */
public class MyRequestMatcher implements RequestMatcher {
	@Override
	public boolean matches(HttpServletRequest request) {
		return false;
	}

}
