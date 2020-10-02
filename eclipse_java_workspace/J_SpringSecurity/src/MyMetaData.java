
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;


/**
 * 	<security:intercept-url  可以实现，这里是编程式的,要和Filter结合使用，没用的？？？？
 * 
 * @author zhaojin
 *
 */
public class MyMetaData  implements FilterInvocationSecurityMetadataSource{

	private Set<String> anonymousURL;
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		Collection<ConfigAttribute>  res=new HashSet<ConfigAttribute> ();
		FilterInvocation invoke=(FilterInvocation)object;
		HttpServletRequest request=invoke.getRequest();
		String uri=request.getRequestURI();
		if(anonymousURL.contains(uri))
		{
			res.add(new SecurityConfig("ROLE_PUBLIC"));
		}
		return res;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
	
}