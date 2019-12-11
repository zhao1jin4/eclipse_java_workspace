package config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@PropertySource(value = "classpath:test.properties") 
@ConfigurationProperties(prefix = "my") //推荐增加 spring-boot-configuration-processor'
@Component
public class ConfigBean {

    private String name;  //自动被设置指定文件中my.name的值
    private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	} 
    
}