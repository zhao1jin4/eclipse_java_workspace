package servlet_noxml_config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/*
 springSecurity初始化  对应于web.xml配置
源码入口类 WebSecurityConfiguration 的 springSecurityFilterChain 方法 
有这个类就不能在web.xml中配置 springSecurityFilterChain
 servlet-3 Spring 自己的  WebApplicationInitializer

public class SecurityWebApplicationInitializer
	extends AbstractSecurityWebApplicationInitializer {

	public SecurityWebApplicationInitializer() {
//		super(WebSecurityConfig.class);//如果当前没Spring,SpringMVC这里放配置类，如有注释
	}
}
 */
 