package security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication 
public class MainSpringBootSecurity 
{
	
	public static void main(String[] args) {
	/**
SpringApplication 加载 application.properties 或applicaion.yml  优先级依次为
1. 当前目录下的 config目录
2. 当前目录   (也就是说application.properties配置文件可.jar外的同级目录)
3. classpath /config 包
4. classpath 根

也可不叫application.properties  
$ java -jar myproject.jar --spring.config.name=myproject  
		
也可同时指定位置和名字
$ java -jar myproject.jar --spring.config.location=classpath:/default.properties,classpath:/override.properties	
	 */
		//java -jar myproject.jar  --spring.config.name=classpath:/security_/sec-application
		
		System.setProperty("spring.config.name", "sec-application");//这种也可以
		SpringApplication.run(MainSpringBootSecurity.class, args);//jar 启动 可以登录(sec-application.properties放jar包同目录) ，war启动不行
	}
}
