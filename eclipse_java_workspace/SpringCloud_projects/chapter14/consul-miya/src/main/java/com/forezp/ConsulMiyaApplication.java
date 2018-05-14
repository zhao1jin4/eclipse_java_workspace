package com.forezp;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 服务健康监测 ,key/value 存储
https://www.consul.io/downloads.html
下载 win zip包,就一个consul命令

consul agent -dev 启动
http://localhost:8500 
启动main方法 后，观察被注册了
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ConsulMiyaApplication {

	@RequestMapping("/hi")
	public String home() {
		return "hi ,i'm miya";
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(ConsulMiyaApplication.class).web(true).run(args);
	}
}
