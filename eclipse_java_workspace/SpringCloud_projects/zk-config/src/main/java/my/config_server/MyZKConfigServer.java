package my.config_server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
  测试注册两个服务
spring:
  application:
    name: testZookeeperApp
  profiles:
    active: dev
  cloud:
    zookeeper:
      connect-string: localhost:2181
      discovery:
        enabled: true
      config:
        #root: configuration # 默认值为 config
        #profileSeparator: '::' # 默认值为,
        defaultContext: apps  
        enabled: true
        watcher:
          enabled: false  	
  maven用    
  org.springframework.cloud:spring-cloud-starter-zookeeper-config
  org.springframework.boot:spring-boot-starter-web
  
  日志提示
  {name='zookeeper', propertySources=[ZookeeperPropertySource {name='config/testZookeeperApp,dev'}, ZookeeperPropertySource {name='config/testZookeeperApp'}, ZookeeperPropertySource {name='config/apps,dev'}, ZookeeperPropertySource {name='config/apps'}]}

*/
@SpringBootApplication
@RestController
public class MyZKConfigServer {
	 @Value("${spring.application.name}")
	 private String instanceName;
 
	@Value("${server.port}")
	String port;
	
	/*
	 zkCli.sh 中
	 > create /config
	 > create /config/testZookeeperApp,dev
	 > create /config/testZookeeperApp,dev/zkPass 123 
	 
	 > delete  /config/testZookeeperApp,dev/zkPass 
	 > create /config/testZookeeperApp,dev/zkPass  123  digest:myuser:CmVSQ2nhuKrMPNW7BK6HrthawaY=:crwda
	 # myuser:CmVSQ2nhuKrMPNW7BK6HrthawaY=是使用DigestAuthenticationProvider.generateDigest("myuser:mypass")生成的
	 > set  /config/testZookeeperApp,dev/zkPass 456 
	 */
	@Value("${zkPass}")
	String password;
	
	//请求  http://localhost:8081/zkConfig  测试成功
	@RequestMapping("/zkConfig")
	public String hi() {
		return "password="+password+" from "+port +",name="+instanceName;
	}
	//
	// POST 请求 /refresh  路径 来刷新，自动刷新（Zookeeper）还未实现。 测试没有这个路径？？？
	public static void main(String[] args) {
		SpringApplication.run(MyZKConfigServer.class, args);
	}
	
}
