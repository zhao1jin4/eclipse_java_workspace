package com.forezp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
@EnableZuulProxy  //Zuul  
@EnableEurekaClient
@SpringBootApplication //会加载到MyFilter
public class ServiceZuulApplication {
	//-- Zuul  默认和Ribbon结合   route and filter  
	//测试
	//http://localhost:8769/api-a/hi?name=lisi
	//http://localhost:8769/api-b/hi?name=lisi&token=123
	//http://localhost:8769/service-ribbon/hi?name=lisi 默认这个可以仿问 如不加路由 routes:别名 配置
	
	//http://localhost:8769/api-hi/hi?name=lisi&token=123 #负载均衡     
			
	//http://localhost:8769/api/api-a/hi?name=lisi   #prefix
	
	/*如请求路径以是/zuul/*    格式可以跳过DispatcherServlet ，如上传大小的限制
	-- application.yml 
	hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds: 60000
	ribbon:
	  ConnectTimeout: 3000
	  ReadTimeout: 60000
禁用某个filter
  zuul.<SimpleClassName>.<filterType>.disable=true
  
  
curl -v -H "Transfer-Encoding: chunked" \
    -F "file=@mylarge.iso" localhost:9999/zuul/simple/file
    
	  */
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceZuulApplication.class, args);
	}
	
	//api-hi-v1 测试不行，原因可能为名字中有-
	//http://localhost:8769/v1/api-hi/hi?name=lisi&token=123 
	@Bean 
	public PatternServiceRouteMapper serviceRouteMapper() {
	    return new PatternServiceRouteMapper(
	        "(?<name>^.+)-(?<version>v.+$)",
	        "${version}/${name}");
	}

}
