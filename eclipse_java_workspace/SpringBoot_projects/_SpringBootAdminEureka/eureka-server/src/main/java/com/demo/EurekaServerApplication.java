package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer   
@SpringBootApplication
public class EurekaServerApplication {
	//http://127.0.0.1:8761/ 进入eureka管理UI
	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}
}
