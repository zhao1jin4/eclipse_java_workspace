package com.forezp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

@SpringBootApplication 
@EnableSidecar  //里面有@EnableCircuitBreaker ， @EnableZuulProxy
public class ServiceSideCarApplication {
	//项目注册到eureka上,可以显示这个服务
	//就可以用ribbon (resetTemplate)项目 仿问sidecar服务，就会转发到其它语言上( node.js  8000 )
	//要求node.js要和sidecar服务要在同一台机器上  Run the resulting application on the same host as the non-JVM application.
	//如要不是一台机器配置eureka.instance.hostname未试
	public static void main(String[] args) {
		SpringApplication.run(ServiceSideCarApplication.class, args);
	} 
 
}
