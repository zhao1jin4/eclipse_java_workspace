package com.forezp.servicegateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@EnableEurekaClient
public class ServiceGatewayApplication {
	
	
	// .discovery.locator.enabled  为 true 时 localhost:8081/service-hi/hi?name=1323
	//如配置了routes:..就两个都可仿问
	//  .discovery.locator.enabled 改为false 就可 localhost:8081/demo/hi?name=1323   就不能仿问 /service-hi/hi?name=1323
    public static void main(String[] args) {
        SpringApplication.run( ServiceGatewayApplication.class, args );
    }
    // http://127.0.0.1:8081/not 这是调用不到的
    @RequestMapping("/not")
    public String not() {
        return "not";
    }

    @Bean
    public RequestTimeGatewayFilterFactory requestTimeGatewayFilterFactory() {
        return new RequestTimeGatewayFilterFactory();
    }
}
