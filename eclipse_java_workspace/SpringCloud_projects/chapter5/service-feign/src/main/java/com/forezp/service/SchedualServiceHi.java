package com.forezp.service;
 
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import feign.Param;
import feign.RequestLine;
 
//基于接口的注解 负载均衡客户端
@FeignClient(value = "service-hi", // 同@LoadBalanced 
//	configuration= {config.FooConfiguration.class},//不能和@SpringBootApplication在同一包下
//fallback = SchedualServiceHiHystric.class //调用服务全断   不会调用对应的错误实现类的方法???? 
fallbackFactory=SchedualServiceHiFactory.class //方式二
		)
public interface SchedualServiceHi {
	
	//-对 关闭  configuration= {config.FooConfiguration.class},feign.Contract.Default  
	@RequestMapping(value = "/hi",method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam("name") String name); // 使用@RequestParam 
   
	//--对打开  configuration= {config.FooConfiguration.class},feign.Contract.Default  
	@RequestMapping(value = "/feignMVC",method = RequestMethod.GET)
	@RequestLine("GET /feignMVC/{owner}/") //@RequestLine  参数加{}
	String feignMVC(@Param("owner") String owner); //使用@Param ,service-hi的参数也是{}
	
}
