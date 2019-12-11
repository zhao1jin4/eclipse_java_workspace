package my.zk.discovery.client;
 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication 
@EnableFeignClients  //负载均衡客户端
@EnableDiscoveryClient
public class MyClientApplication {
	 //http://localhost:8083/services
		
	//http://127.0.0.1:8083/hi?name=lisi  测试成功
	 //http://127.0.0.1:8083/reset?name=lisi 测试成功 (ribbon或loadbalancer)
	//zk discovery 如服务变化，(如增加一个节点)，客户端要等一会才会做负载
	
	//http://127.0.0.1:8083/loadbalancer?name=lisi  //loadbalancer替代ribbon,  Resilience4J  替代老的 hystrix
	public static void main(String[] args) {
		SpringApplication.run(MyClientApplication.class, args);
	}
 
	@Bean
	@LoadBalanced //表明这个restRemplate开启负载均衡的功能,ribbon或balancer
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	 
	 
}
