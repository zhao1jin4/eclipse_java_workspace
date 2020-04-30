package springboot_shiro;

import java.net.URL;
import java.util.Enumeration;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
public class SpringBootShiroMain {
	
	
	//测试中？？？
	
//	//--配置*.mvc  空项目 也不行
//		@Bean
//		public ServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet) {
//			ServletRegistrationBean bean = new ServletRegistrationBean(dispatcherServlet);
//			bean.addUrlMappings("*.mvc");
//			return bean;
//		} 
 
	
	//日志显示  要么有Realm 的@Bean 要么放 shiro.ini文件(不能两个一起使用)
	//要求 在classpath下(src/main/resources/shiro.ini)或META-INF下(src/main/resources/META-INF/shiro.ini) 一定要有帐户数据
		
	@Bean
	public Realm realm(HashedCredentialsMatcher credentialsMatcher)  
	{ 
		MySpringRealm realm=new MySpringRealm();
		realm.setCredentialsMatcher(credentialsMatcher);
		return realm;
	}
	@Bean
	public HashedCredentialsMatcher credentialsMatcher()
	{
		HashedCredentialsMatcher credentialsMatcher=new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("md5");
		credentialsMatcher.setHashIterations(3);
		return credentialsMatcher;
	}
	
	
	@Bean  //这个权限缓存有效果
	protected CacheManager cacheManager() {
	    return new MemoryConstrainedCacheManager();
	}
	
	
	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
	    DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
	    chainDefinition.addPathDefinition("/**/*.html", "anon");
	    chainDefinition.addPathDefinition("/**/*.js", "anon");
	    chainDefinition.addPathDefinition("/**/*.jpg", "anon");
	    chainDefinition.addPathDefinition("/main", "anon");
   	    chainDefinition.addPathDefinition("/test", "anon");
	    //chainDefinition.addPathDefinition("/initLoginNoFilter", "anon");
	    chainDefinition.addPathDefinition("/submitLoginNoFilter", "anon"); 
	    chainDefinition.addPathDefinition("/logout", "logout");
	    chainDefinition.addPathDefinition("/login", "authc"); //不会验证用户名密码？？可能不认j_username？
	    chainDefinition.addPathDefinition("/**", "authc"); 
	    return chainDefinition;
	}

	public static void main(String[] args) throws Exception {
		// https://shiro.apache.org/spring-boot.html
	

		SpringApplication.run(SpringBootShiroMain.class, args);
		
		//---其它
		
		String conf="/META-INF/spring-configuration-metadata.json";
		String add="/META-INF/additional-spring-configuration-metadata.json";
		URL a=SpringBootShiroMain.class.getResource(conf);
		//开发工具的提示properties应该是读所有jar包下的/META-INF/spring-configuration-metadata.json 和 additional-spring-configuration-metadata.json 
		//如 spring-boot-2.2.1.RELEASE.jar,shiro-spring-boot-web-starter-1.5.2.jar
		
		
		//如何得到所有classpath下，下面是读/META-INF/spring.factories的源码,但找不到？？？
		Enumeration<URL> urls= SpringBootShiroMain.class.getClassLoader().getResources(conf);
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
		}
		Enumeration<URL> urls2=	ClassLoader.getSystemResources(conf);
		while (urls2.hasMoreElements()) {
			URL url = urls2.nextElement();
		}
			
	}

}
