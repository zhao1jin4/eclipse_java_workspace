package com.forezp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@RestController
public class ServiceMiyaApplication {
	//java -jar zipkin-server-2.12.9-exec.jar  后就可 http://127.0.0.1:9411/zipkin/  或
	//docker run -d -p 9411:9411 openzipkin/zipkin
	
	//http://localhost:8989/hi 后 zipkin ( http://localhost:9411/ )Find Traces按钮有显示，
	public static void main(String[] args) {
		SpringApplication.run(ServiceMiyaApplication.class, args);
	}

	private static final Logger LOG = Logger.getLogger(ServiceMiyaApplication.class.getName());


	@RequestMapping("/hi")
	public String home(){
		LOG.log(Level.INFO, "hi is being called");
		return "hi i'm miya!";
	}

	@RequestMapping("/miya")
	public String info(){
		LOG.log(Level.INFO, "info is being called");
		return restTemplate.getForObject("http://localhost:8988/info",String.class);
	}

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
}
