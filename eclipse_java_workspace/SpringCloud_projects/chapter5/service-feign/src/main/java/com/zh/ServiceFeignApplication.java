package com.zh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(
		//basePackages= {"com.xx"}
		)  //负载均衡客户端
public class ServiceFeignApplication {
	 //http://127.0.0.1:8765/hi?name=lisi    header AuthenToken=abc
	 
	//http://127.0.0.1:8765/feignMVC?owner=lisi    header AuthenToken=abc
	//http://127.0.0.1:8765/feignMVCByte?owner=lisi 
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceFeignApplication.class, args);
	}
}
