package com.zh.service;
 
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import config.FooConfiguration;
import config.HeaderConfiguration;
import config.JsonLogGzipConfiguration;
import feign.Param;
import feign.RequestLine;
 
//基于接口的注解 负载均衡客户端
@FeignClient(
	value = "service-hi", // 同@LoadBalanced 
	//url="http://127.0.0.1:8762/",//直接连接
	configuration= { //不能和@SpringBootApplication在同一包下
			FooConfiguration.class, 
			HeaderConfiguration.class, 
			JsonLogGzipConfiguration.class},
//fallback = SchedualServiceHiFallback.class //调用服务全断   不会调用对应的错误实现类的方法???? 
fallbackFactory=SchedualServiceHiFactory.class 
		)
public interface SchedualServiceHi {
	//会调用 FeignCircuitBreakerInvocationHandler -> Resilience4JCircuitBreaker，如开启circuitbreaker还是在当前线程
	
	
	//-对 关闭  configuration= {config.FooConfiguration.class},feign.Contract.Default  
//	@RequestMapping(value = "/hi",method = RequestMethod.GET)
//    String sayHiFromClientOne(@RequestParam("name") String name); // 使用@RequestParam 
   
	//--对打开  configuration= {config.FooConfiguration.class},feign.Contract.Default  
	 @RequestLine("GET /feignMVC/{owner}/") //@RequestLine  参数加{}  
	String feignMVC(@Param("owner") String owner); //使用@Param ,service-hi的参数也是{}
	
	 @RequestLine("GET /feignMVC/{owner}/") 
	 ResponseEntity<byte[]>  feignMVCByte(@Param("owner") String owner); 
	 
}
