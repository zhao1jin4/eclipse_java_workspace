//package security_;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
////@SpringBootApplication 
//public class MainSpringBootSecurity 
//{
//	
//	public static void main(String[] args) {
//	/**
//	SpringApplication 加载 application.properties 或applicaion.yml 的顺序
//		A /config subdirectory of the current directory
//		The current directory
//		A classpath /config package
//		The classpath root
//
//也可不叫application.properties  
//$ java -jar myproject.jar --spring.config.name=myproject  
//也可同时指定位置和名字
//$ java -jar myproject.jar --spring.config.location=classpath:/default.properties,classpath:/override.properties	
//	 */
//		
//		//System.setProperty("spring.config.name", "sec-application");
//		SpringApplication.run(MainSpringBootSecurity.class, args);//jar 启动 可以登录 ，war启动不行
//	}
//}
