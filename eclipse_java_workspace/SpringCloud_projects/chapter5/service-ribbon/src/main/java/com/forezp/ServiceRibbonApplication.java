package com.forezp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient //(spring-cloud-starter-ribbon) ribbon 和 @LoadBalanced 一起用      客户端都可做负载均衡,相当于 dubbo consumeer,
@EnableHystrix //断路器(如果几台中一台服务不可用,就把这台服务器隔离了,就仿问不到这台机器了)
@EnableHystrixDashboard//maven 加 spring-boot-starter-actuator 就可以仿问 http://localhost:8764/hystrix  提示 http://hostname:port/turbine/turbine.stream 
//文本框中输入 http://localhost:8764/hystrix.stream ,2000ms,Monitor Stream按钮 -> Dalston版本OK ,Finchley版本一直loading  报JS错误???

@EnableCircuitBreaker  //文本框中输入 http://localhost:8764/actuator/hystrix.stream

public class ServiceRibbonApplication {

	//一种是ribbon(负载均衡客户端)+restTemplate
	//另一种是feign(Feign默认集成了ribbon)
	public static void main(String[] args) {
		SpringApplication.run(ServiceRibbonApplication.class, args);
	}
	//
	//ribbon :  http://localhost:8764/hi?name=lisi
	//hystrix:  http://localhost:8764/hystrix 
	
	@Bean
	@LoadBalanced //表明这个restRemplate开启负载均衡的功能
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}