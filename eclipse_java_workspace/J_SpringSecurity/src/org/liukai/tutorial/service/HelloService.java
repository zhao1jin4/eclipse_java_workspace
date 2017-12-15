package org.liukai.tutorial.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public interface HelloService {
	@Secured({"ROLE_USER"})//要在接口中有效,表示要调用这个方法要用ROLE_USER角色,要有配置<security:global-method-security  secured-annotations="enabled"
	 public String getHello();
	@Secured({"ROLE_ADMIN"})
	 public String setHello(String s);
	
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")//要有配置<security:global-method-security pre-post-annotations="enabled",支持表达式,要有角色
	public void initAdmin();
	
	@PostAuthorize("isAnonymous()")//要有配置<security:global-method-security pre-post-annotations="enabled",只可是未登录用户才可调用
	public void destroy();
	
	@PreAuthorize("isAnonymous()")
	public void init();
	
	//无效???,被<security:protect-pointcut 所拦截
	public void other();
}
