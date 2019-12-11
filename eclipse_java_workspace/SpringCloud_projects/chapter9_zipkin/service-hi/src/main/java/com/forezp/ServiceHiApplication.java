package com.forezp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


//import org.springframework.cloud.sleuth.sampler.AlwaysSampler;//为Dalston版本,Finchley没有AlwaysSampler
import brave.sampler.Sampler;//brave-5.4.3.jar

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@RestController
public class ServiceHiApplication {
	//http://localhost:8988/hi  后 zipkin ( http://localhost:9411/ )Find Traces按钮有显示， 
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceHiApplication.class, args);
	}

	private static final Logger LOG = Logger.getLogger(ServiceHiApplication.class.getName());


	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	@RequestMapping("/hi")
	public String callHome(){
		LOG.log(Level.INFO, "calling trace service-hi  ");
		return restTemplate.getForObject("http://localhost:8989/miya", String.class);
	}
	@RequestMapping("/info")
	public String info(){
		LOG.log(Level.INFO, "calling trace service-hi ");

		return "i'm service-hi";

	}

	
//	@Bean 
//	public  defaultSampler(){ //为Dalston版本,Finchley没有AlwaysSampler
//		return new AlwaysSamplerAlwaysSampler();
//	}
	
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
	//spring.sleuth.sampler.probability (default: 0.1, which is 10 percent). 
	 
}
