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
         ShiroConfiguration.class, //�ĵ���˵���������SecurityManager
         ShiroAnnotationProcessorConfiguration.class})
public class CliAppConfig {
	@Bean
	public Realm realm() {
		return new MyRealm();
	}
	
	
	@Autowired  //ע�벻�ˣ�����
	private org.apache.shiro.mgt.SecurityManager securityManager;
	 
	//���²�Ҫ��webӦ����ʹ��
	 @PostConstruct
	 private void initStaticSecurityManager() {
	     SecurityUtils.setSecurityManager(securityManager);
	 }
	 //�Ϳ��� SecurityUtils.getSubject();
	 
}
 