package jsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
//@EnableAutoConfiguration

@ServletComponentScan (basePackages= {"jsp"}) //就可以自动把@WebServlet,@WebListener,@WebFilter 等servlet自动注册 
//在thymeleaf中也有配置
public class JSPApplication {
    //要打成我war包 , 或右击项目run as -> tomcat
	//http://localhost:8080/springboot_jsp/jsp
	public static void main(String[] args) {
		
		/**
		SpringApplication 加载 application.properties 或applicaion.yml  优先级依次为
			1. 当前目录下的 config目录
			2. 当前目录   (也就是说application.properties配置文件可.jar外的同级目录)
			3. classpath /config 包
			4. classpath 根
	
	也可不叫application.properties  
	$ java -jar myproject.jar --spring.config.name=jsp-application   会覆盖以前application
	也可同时指定位置和名字
	$ java -jar myproject.jar --spring.config.location=classpath:/default.properties,classpath:/override.properties	
		 */
			
		System.setProperty("spring.config.name", "jsp-application,application");//会覆盖默认的application,除非,分隔加上默认的
		//System.setProperty("spring.config.location", "classpath:/jsp/jsp-application.properties");//会覆盖默认搜索位置
		SpringApplication.run(JSPApplication.class, args);
	}
	
	//--配置*.mvc
	@Bean
	public ServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean bean = new ServletRegistrationBean(dispatcherServlet);
		bean.addUrlMappings("*.mvc");
		return bean;
	} 
	
}
