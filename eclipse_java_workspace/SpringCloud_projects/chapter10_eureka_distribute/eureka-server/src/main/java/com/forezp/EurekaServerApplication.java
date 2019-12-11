package com.forezp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}
	/**
	echo 127.0.0.1 peer1 >>  C:\Windows\System32\drivers\etc\hosts
	echo 127.0.0.1 peer2 >>  C:\Windows\System32\drivers\etc\hosts
	
	etc/hosts
 		127.0.0.1 peer1
		127.0.0.1 peer2
java EurekaServerApplication --spring.profiles.active=peer1
java EurekaServerApplication --spring.profiles.active=peer2
两个服务可同时启动，相互依赖,启动中间有连接错误可忽略

http://127.0.0.1:8761
http://127.0.0.1:8769  
每个页面中 registered-replicas列 和  available-replicas列 是对方机器的

	 */
}
