package shiro_web;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.env.IniWebEnvironment;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;

public class ConfigClass {
	ShiroFilter filter;
	IniWebEnvironment web;
	EnvironmentLoaderListener listener;
	//------filter
	AnonymousFilter anon;
	FormAuthenticationFilter authc;
	RolesAuthorizationFilter roles;
	PermissionsAuthorizationFilter perms;
	LogoutFilter logout;
	
	 DefaultFilter all;// anon 定义在 DefaultFilter 中
	 //---spring
	 EhCacheManager m;
	 EhCacheManagerFactoryBean f;
	 HashedCredentialsMatcher h;
}
