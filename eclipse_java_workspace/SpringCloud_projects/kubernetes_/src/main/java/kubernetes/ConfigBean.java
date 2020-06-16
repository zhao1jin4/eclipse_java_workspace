package kubernetes;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//刷新配置
@ConfigurationProperties(prefix = "myprop") //推荐增加 spring-boot-configuration-processor
@Component
public class ConfigBean {

   private String name;   //自动被设置指定文件中myprop.name的值
   private String greeting;  
   
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
        
    public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}
    
    
}