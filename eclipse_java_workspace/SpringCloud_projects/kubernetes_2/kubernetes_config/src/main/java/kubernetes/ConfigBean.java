package kubernetes;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

//@RefreshScope 或 @ConfigurationProperties  configmap修改时，会自动触发重启刷新( mode: event,strategy: restart_context) 
@ConfigurationProperties(prefix = "myprop") //推荐增加 spring-boot-configuration-processor
@Component
public class ConfigBean {

   private String name;   //自动被设置指定文件中myprop.name的值
   private String password; 
   private String greeting;  
   
	public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

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