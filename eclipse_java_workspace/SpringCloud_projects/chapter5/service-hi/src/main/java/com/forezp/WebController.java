package com.forezp;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class WebController {
	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
	private Environment env;

	@PostConstruct
	public void init()
	{
		//不行？？
		this.port=ctx.getEnvironment().getProperty("server.port");
		System.out.println("String: " +env.getProperty("server.port") + 1111);

	}
	//@Value("${server.port}")//  cloud-2020,boot-2.4 不能识别 @Value("${server.port}")
	String port;
}
