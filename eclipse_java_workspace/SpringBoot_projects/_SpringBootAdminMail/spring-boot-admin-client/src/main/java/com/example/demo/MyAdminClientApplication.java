package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyAdminClientApplication {
	public static void main(String[] args) {
		//如果有 Spring Cloud Discovery (如Eureka) 就不需要Spring Boot Admin客户端了,
		//如客户端上线或者下载有邮件通知
		SpringApplication.run(MyAdminClientApplication.class, args);
	}
}
