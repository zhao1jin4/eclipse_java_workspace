package security;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication 
public class MainSpringBootSecurity 
{
	
	public static void main(String[] args) {
	/**
SpringApplication 加载 application.properties 或applicaion.yml  优先级依次为
1. classpath 根
2. classpath /config 包
3. 当前目录   (也就是说application.properties配置文件可.jar外的同级目录)
4. 当前目录下的 config目录



也可不叫application.properties  
$ java -jar myproject.jar --spring.config.name=myproject  
		
也可同时指定位置和名字
$ java -jar myproject.jar --spring.config.location=classpath:/default.properties,classpath:/override.properties	
	 */
		
		System.out.println(new File(".").getAbsolutePath());

		//加 optional: 就不行了,要build保证target目录有配置文件，IDE可以使用
		//java -jar target/springboot_security.jar  --spring.config.location=classpath:/config/ --spring.config.name=sec-application  
		//java -jar target/springboot_security.jar  --spring.config.location=classpath:/config/sec-application.properties 
		
		//只以下两个结合 ，IDE测试成功
		System.setProperty("spring.config.location", "classpath:config/"); //目录必须以/结尾
		System.setProperty("spring.config.name", "sec-application"); 
		
		//config目录放在src/main/java下打成jar包，配置文件丢失，要放在src/main/resources
		SpringApplication.run(MainSpringBootSecurity.class, args);
	}
}
