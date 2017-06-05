package spring3_annotation_config;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


//@EnableCaching 
@Configuration //���沿��spring������XML,<bean id=""
@Import({ServiceConfig.class}) //�е���Ҳ��@Configuration  
@PropertySource("classpath:jdbc.properties")// д��Environment��, /WEB-INF
public class AppConfig 
{
	 @Inject 
	 Environment env; //OK
	 
	//--  private @Value("#{jdbcProperties.password}") String password;//<util:properties id="jdbcProperties"
	//--  private @Value("${jdbc.username}") String jdbcUser; // <context:property-placeholder �е�ֵ
	
	 @Value("#{systemProperties['os.name']}") String home; //OK

	 @Value("#{now.time}") String timestamp;//beanId.property OK
	
	 public  static  String username;
	
	@Bean 
	public Date now() {
		return new Date();
	}
	@Bean // <bean id=""
	public UserDAO userDAO() {
		UserDAO dao=new UserDAOImpl();
		//dao.changeTest(env.getProperty("jdbc.username"));
		//dao.changeTest(timestamp);
		dao.changeTest(home);
		return dao;
	}
}
