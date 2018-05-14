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
	etc/hosts
 		127.0.0.1 peer1
		127.0.0.1 peer2
java EurekaServerApplication --spring.profiles.active=peer1
java EurekaServerApplication --spring.profiles.active=peer2

	 */
}
