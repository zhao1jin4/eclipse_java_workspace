package shiro_spring_anno_main;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.config.ShiroAnnotationProcessorConfiguration;
import org.apache.shiro.spring.config.ShiroBeanConfiguration;
import org.apache.shiro.spring.config.ShiroConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ShiroBeanConfiguration.class,
         ShiroConfiguration.class, //文档上说，这个创建SecurityManager
         ShiroAnnotationProcessorConfiguration.class})
public class CliAppConfig {
	@Bean
	public Realm realm() {
		return new MyRealm();
	}
	
	
	@Autowired  //注入不了？？？
	private org.apache.shiro.mgt.SecurityManager securityManager;
	 
	//以下不要在web应用中使用
	 @PostConstruct
	 private void initStaticSecurityManager() {
	     SecurityUtils.setSecurityManager(securityManager);
	 }
	 //就可以 SecurityUtils.getSubject();
	 
}
 