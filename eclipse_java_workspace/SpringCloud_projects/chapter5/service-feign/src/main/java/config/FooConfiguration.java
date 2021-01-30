package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import feign.Contract;
import feign.Feign;
//也是不能放在@SpringBootApplication包下
@Configuration
public class FooConfiguration  
{
    @Bean
    public Contract feignContract() {
    	//要使用 @RequestLine("GET /hi/{name}/")
        return new feign.Contract.Default();
    }

	
	/*
	再加如下相配置，就可以有详细的openFeign请求返回日志  
	logging:
	  level:
       #feogn日志以什么级别监视那个接口
        com.xxFeignService: debug
	 */
	@Bean 
    feign.Logger.Level feignLoggerLevel(){
        return feign.Logger.Level.FULL;
    } 
	
	
    //eureka用户名密码
//    @Bean
//    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
//        return new BasicAuthRequestInterceptor("user", "password");
//    }
	
    //为某个feign禁用 CircuitBreaker 
//    @Bean
//	@Scope("prototype")
//	public Feign.Builder feignBuilder() {
//		return Feign.builder();
//	}
	
	
	
}