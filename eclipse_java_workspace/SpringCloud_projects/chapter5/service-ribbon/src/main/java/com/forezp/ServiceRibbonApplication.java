package com.forezp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//ribbon是一个客户端负载均衡

@SpringBootApplication
@EnableDiscoveryClient 

//@EnableHystrix //断路器(如果几台中一台服务不可用,就把这台服务器隔离了,就仿问不到这台机器了)
@EnableCircuitBreaker //可以替代 @EnableHystrix

@EnableHystrixDashboard//maven 加 spring-boot-starter-actuator 就可以仿问 http://localhost:8764/hystrix  提示 http://hostname:port/turbine/turbine.stream 
//文本框中输入 http://localhost:8764/hystrix.stream (http://localhost:8764/actuator/hystrix.stream)
	//,2000ms,Monitor Stream按钮 ->  Greenwitch 版本 OK
  

//对这个服务的负载方式默认的轮循可修改为随机
//@RibbonClient(name="SERVICE-HI",configuration=config.Config.class)
public class ServiceRibbonApplication {

	//一种是ribbon(负载均衡客户端)+restTemplate
	//另一种是feign(Feign默认集成了ribbon)
	
	//Hoxton版本开始 Ribbon变维护模式,使用loadBalancer，Resilience4J 替代hystrix 见zk-discovery-client项目
	public static void main(String[] args) {
		SpringApplication.run(ServiceRibbonApplication.class, args);
	}
	//
	//ribbon :  http://localhost:8764/hi?name=lisi   http://localhost:8764/choose
	//hystrix:  http://localhost:8764/hystrix 
	
	@Bean
	@LoadBalanced //(spring-cloud-starter-ribbon)   和 @LoadBalanced 一起用      客户端都可做负载均衡,相当于 dubbo consumer 
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	

}