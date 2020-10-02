

import java.util.Collection;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyAuthenticationProvider implements AuthenticationProvider {
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		
		Collection<?> auths=auth.getAuthorities();
		String username=auth.getName();
		Object p=auth.getPrincipal();
		if(p instanceof UserDetails)
		{
			UserDetails details =(UserDetails)p;
		}
		Object c=auth.getCredentials();
	 
		return null;
	}
	public boolean supports(Class<?> clazz) {
		return false;
	}

}
