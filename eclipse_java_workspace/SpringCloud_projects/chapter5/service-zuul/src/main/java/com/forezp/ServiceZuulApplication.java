package com.forezp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy  //Zuul  
@EnableEurekaClient
@SpringBootApplication //会加载到MyFilter
public class ServiceZuulApplication {
	//-- Zuul  默认和Ribbon结合   route and filter  
	//测试
	//http://localhost:8769/api-a/hi?name=lisi
	//http://localhost:8769/api-b/hi?name=lisi&token=123
		
	public static void main(String[] args) {
		SpringApplication.run(ServiceZuulApplication.class, args);
	}



}
