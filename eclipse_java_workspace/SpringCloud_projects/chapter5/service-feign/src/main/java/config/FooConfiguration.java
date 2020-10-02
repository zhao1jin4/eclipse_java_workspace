package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import feign.Contract;
import feign.Feign;
//也是不能放在@SpringBootApplication包下
@Configuration
public class FooConfiguration {
    @Bean
    public Contract feignContract() {
    	//要使用 @RequestLine("GET /hi/{name}/")
        return new feign.Contract.Default();
    }

    //eureka用户名密码
//    @Bean
//    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
//        return new BasicAuthRequestInterceptor("user", "password");
//    }
    //为某个feign禁用hystrix
//    @Bean
//	@Scope("prototype")
//	public Feign.Builder feignBuilder() {
//		return Feign.builder();
//	}
}