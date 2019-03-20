package com.forezp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {
//	http://localhost:8889/  查看界面 ,如 config server,config client都启动了，就都能看到
	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}
}
