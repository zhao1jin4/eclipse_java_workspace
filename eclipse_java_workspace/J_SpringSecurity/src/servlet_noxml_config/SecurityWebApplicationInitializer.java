package servlet_noxml_config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/*
入口类 WebSecurityConfiguration 的 springSecurityFilterChain 方法  对应于web.xml配置
  有这个类就不能在web.xml中配置 springSecurityFilterChain
 servlet-3 Spring 自己的  WebApplicationInitializer

public class SecurityWebApplicationInitializer
	extends AbstractSecurityWebApplicationInitializer {

	public SecurityWebApplicationInitializer() {
		super(WebSecurityConfig.class);
	}
}
 */
 