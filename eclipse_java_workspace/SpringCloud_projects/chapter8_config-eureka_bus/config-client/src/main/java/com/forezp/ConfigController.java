package com.forezp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope  //不能和@Configuration 放在一起用，actuator (bus)  使用 /actuator/refresh 
//@RefreshScope works (technically) on an @Configuration class, but it might lead to surprising behavior
// http://localhost:8882/actuator/refresh  测试请求404

public class ConfigController {
	
	@Value("${foo}")
	String foo;

	@RequestMapping(value = "/hi")
	public String hi(){
		return foo;
	}
}
