package org.liukai.tutorial.service;

import java.util.Collection;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

public class MyAuthenticationProvider implements AuthenticationProvider {
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		
		Collection<?> auths=auth.getAuthorities();
		String username=auth.getName();
		Object p=auth.getPrincipal();
		Object c=auth.getCredentials();
		return null;
	}
	public boolean supports(Class<?> clazz) {
		return false;
	}

}
