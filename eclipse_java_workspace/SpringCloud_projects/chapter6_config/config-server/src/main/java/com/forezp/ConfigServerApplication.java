package com.forezp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;


@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

	public static void main(String[] args) {
		// http://localhost:8888/foo/dev  有返回JSON表示可以从客户端取   {name}/{profile}
		
		/* cd E:/tmp/git_config
		   git init (本地 file:/ 也可不做这步)
		   E:\tmp\git_config\repos\config-client-dev.yml 文件中有 foo: bar-dev
		    
		  http://127.0.0.1:8888/master/config-client-dev.yml (已经是解密的,如不想 spring.cloud.config.server.encrypt.enabled=false) 即/{label}/{application}-{profile}.yml格式
		  http://127.0.0.1:8888/config-client/dev 返回一个json格式 即 /{application}/{profile}[/{label}]
		  */
		SpringApplication.run(ConfigServerApplication.class, args);
	}
}
