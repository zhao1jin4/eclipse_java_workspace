package com.demo.gateway_k8s;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
 

@SpringBootApplication
@EnableDiscoveryClient

public class GatewayK8sApplication {  
	
	public static void main(String[] args) {
        SpringApplication.run( GatewayK8sApplication.class, args );
    }
	
	/* centos71.com是搭建的 harbor
docker build . -t centos71.com/library/gateway-kubernetes:1.0
 docker push  centos71.com/library/gateway-kubernetes:1.0

#docker image save -o gateway-kubernetes-1.0.tar gateway-kubernetes:1.0  
#docker load -i gateway-kubernetes-1.0.tar 
	 */


	 //curl http://myk8s-app:9000/showIp  OK 显示的是pod的地址，是切换节点显示的
	
	
	// curl  http://127.0.0.1:8082/remote/showIp  # gateway 测试 调用另一个项目，k8s的serviceclusterIP 用lb:不行的
	   
	
	
}
