package jsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//@EnableAutoConfiguration

@ServletComponentScan (basePackages= {"jsp"}) //就可以自动把@WebServlet,@WebListener,@WebFilter 等servlet自动注册 
//在thymeleaf中也有配置
public class JSPApplication {
	//javax.el.ExpressionFactory x;
	public static void main(String[] args) {
		
		/**
		SpringApplication 加载 application.properties 或applicaion.yml 的顺序
			A /config subdirectory of the current directory
			The current directory
			A classpath /config package
			The classpath root
	
	也可不叫application.properties  
	$ java -jar myproject.jar --spring.config.name=myproject  
	也可同时指定位置和名字
	$ java -jar myproject.jar --spring.config.location=classpath:/default.properties,classpath:/override.properties	
		 */
			
//		System.setProperty("spring.config.name", "jsp-application");
//		System.setProperty("spring.config.location", "classpath:/jsp/jsp-application.properties");
		SpringApplication.run(JSPApplication.class, args);
	}
}
