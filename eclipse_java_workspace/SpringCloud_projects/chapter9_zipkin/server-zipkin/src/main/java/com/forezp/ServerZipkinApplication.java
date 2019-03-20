package com.forezp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer//为Dalston版本,Finchley过时了
//java -jar zipkin-server-2.11.12-exec.jar  后就可 http://127.0.0.1:9411/zipkin/  或
//docker run -d -p 9411:9411 openzipkin/zipkin

public class ServerZipkinApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerZipkinApplication.class, args);
	}
}
