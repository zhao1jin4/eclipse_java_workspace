package com.forezp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;


@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

	public static void main(String[] args) {
		 // http://localhost:8888/foo/dev  有返回JSON表示可以从客户端取   {name}/{profile}
		SpringApplication.run(ConfigServerApplication.class, args);
	}
}
