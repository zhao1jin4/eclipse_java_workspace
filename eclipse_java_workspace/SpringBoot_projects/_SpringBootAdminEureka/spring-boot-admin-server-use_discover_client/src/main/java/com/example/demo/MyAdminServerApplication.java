package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@EnableAdminServer //增加
//@SpringBootApplication
//也可使用
@Configuration  @EnableAutoConfiguration

public class MyAdminServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyAdminServerApplication.class, args);
		// http://localhost:8769 
	}
}
