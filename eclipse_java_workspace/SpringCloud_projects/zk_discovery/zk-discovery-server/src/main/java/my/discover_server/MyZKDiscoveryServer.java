package my.discover_server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
  测试注册两个服务
  spring:
  cloud:
    zookeeper:
      connect-string: localhost:2181
      discovery:
        enabled: true
        
  maven用    
  org.springframework.cloud:spring-cloud-starter-zookeeper-discovery
  org.springframework.boot:spring-boot-starter-web
  
zookeeper 下   /services/testZookeeperApp 启动两个服务就有两个记录

*/
@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class MyZKDiscoveryServer {
	
	@Value("${server.port}")
	String port;
	
	//请求  http://localhost:8081/hi?name=lisi
	@RequestMapping("/hi")
	public String hi(@RequestParam  String name) {
		return "Hello World! from "+port +",name="+name;
	}
	
	//修改端口启动两个
	public static void main(String[] args) {
		SpringApplication.run(MyZKDiscoveryServer.class, args);

	}

	@Value("${spring.application.name}")
	private String instanceName;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	//请求  http://localhost:8081/services 显示有多个服务注册上去
    @GetMapping("/services")
    public List<String> serviceUrl() {
        List<ServiceInstance> list = discoveryClient.getInstances(instanceName);
        List<String> services = new ArrayList<>();
        if (list != null && list.size() > 0 ) {
            list.forEach(serviceInstance -> {
                services.add(serviceInstance.getUri().toString());
            });
        }
        return services;
    }
 
}  
