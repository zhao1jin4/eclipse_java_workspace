package spring_boot_security_oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration  //spring-mvc.xml
@EnableWebMvc
@EnableGlobalMethodSecurity(//和Configuration放一起
			securedEnabled = true// 方法上做权限 @Secured的总开关 
			,prePostEnabled = true //方法上做权限@PreAuthorize,@PostAuthorize的总开关  
		) 
//@PreAuthorize("hasAnyAuthority('p1')")     @PreAuthorize("isAnonymous()")
//  @Secured("IS_AUTHENTICATED_ANONYMOUSLY")   @Secured("ROLE_XX") 建议放在controller方法 
public class MvcConfig implements WebMvcConfigurer {

	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login-view").setViewName("login");
		//跳转到登录页
	}

	@Bean
	public InternalResourceViewResolver viewResolver()
	{
		InternalResourceViewResolver viewResolver=new  InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
}
