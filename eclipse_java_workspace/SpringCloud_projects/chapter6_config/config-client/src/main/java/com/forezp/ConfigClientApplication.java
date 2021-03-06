package com.forezp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ConfigClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConfigClientApplication.class, args);
	}
	// http://127.0.0.1:8882/hi 
	@Value("${foo}")  //读服务端文件名格式  {application}-{profile}.properties 中的foo=x
	String foo;
	
	@Value("${pass}")   
	String pass;
	
	@RequestMapping(value = "/hi")
	public String hi(){
		return foo;
	} 
	 
	
	// http://127.0.0.1:8882/pass 
	@RequestMapping(value = "/pass")
	public String pass(){
		 return pass; 
	}
}
