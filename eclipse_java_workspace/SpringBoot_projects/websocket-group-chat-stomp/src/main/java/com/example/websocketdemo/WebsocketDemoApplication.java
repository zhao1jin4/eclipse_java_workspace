package com.example.websocketdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebsocketDemoApplication {

	public static void main(String[] args) {
		//https://github.com/callicoder/spring-boot-websocket-chat-demo 项目的 的示例代码
		//可向session中存信息 ,@EventListener, messagingTemplate
		
		SpringApplication.run(WebsocketDemoApplication.class, args);
	}
}
