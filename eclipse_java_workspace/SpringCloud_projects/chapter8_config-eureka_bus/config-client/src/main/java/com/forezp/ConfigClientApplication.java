package com.forezp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ConfigClientApplication {
	
	public static void main(String[] args) {
		// http://localhost:8881/hi ,http://localhost:8882/hi  //显示值
		//http://127.0.0.1:8888/config-client/dev
			
		/*bus 启动两个config-client 端口为：8881、8882。
		
		 POST 请求 (Firefox的 RESTClient)
		 http://localhost:8881/bus/refresh(老Dalston版本) 
		 http://localhost:8881/actuator/bus-refresh   (新的 Content-Type : application/json) 
		 					也可部分刷新(如增量部署)加参数如 /bus-refresh/customers:9000 或 /bus-env/customers:**
							 (customers是service ID是在bus上的一个实例，每个节点设置spring.application.index不同的值)
		// 后  :8881/hi 和 :8882/hi 都变了(不用重启服务)
		*/
		SpringApplication.run(ConfigClientApplication.class, args);
	}

}
