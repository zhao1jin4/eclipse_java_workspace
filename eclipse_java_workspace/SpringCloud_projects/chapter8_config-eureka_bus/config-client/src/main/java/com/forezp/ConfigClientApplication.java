package com.forezp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
@RefreshScope  //bus
public class ConfigClientApplication {

	@Value("${foo}")
	String foo;
	public static void main(String[] args) {
		// http://localhost:8881/hi ,http://localhost:8882/hi  //显示值
		
		/*bus 启动两个config-client 端口为：8881、8882。
		
		 POST 请求 (Firefox的 RESTClient)
		 http://localhost:8881/bus/refresh(老Dalston版本) 
		 http://localhost:8881/actuator/bus-refresh   (新的 Content-Type : application/json) 
		 					也可加参数如 /bus-refresh/customers:9000 或 /bus-env/customers:**
		// 后  :8881/hi 和 :8882/hi 都变了(不用重启服务)
		*/
		SpringApplication.run(ConfigClientApplication.class, args);
	}


	@RequestMapping(value = "/hi")
	public String hi(){
		return foo;
	}
}
