package com.forezp;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

 

@SpringBootApplication
//@EnableEurekaClient  //相当于  dubbo provider ,也可以使用通用的 @EnableDiscoveryClient
@EnableDiscoveryClient
@RestController
public class ServiceHiApplication {
	//启动两个服务  http://localhost:8762/hi?name=lisi    响应gzip
				 //http://localhost:8763/hi?name=lisi
	public static void main(String[] args) {
		SpringApplication.run(ServiceHiApplication.class, args);
	}

 
	@Value("${server.port}")//  cloud-2020,boot-2.4 不能识别 @Value("${server.port}"),也不能正常的注册到eureka 上
	String port="123";
	
	@RequestMapping("/hi")
	public String home(HttpServletRequest request,@RequestParam String name) {
		 
		String header=null;
		for(Enumeration<String> enumerate=request.getHeaderNames();enumerate.hasMoreElements();  header=enumerate.nextElement())
		{
			if(header!=null)
			{ 
				String val=request.getHeader(header);
				System.out.println("header name="+header+",val="+val); 
			} 
		}
		return "hi "+name+",i am from port:" +port;
	}
	 
	//http://127.0.0.1:8762/queryUsers?name=hi
	//http://127.0.0.1:8763/queryUsers?name=hi
	@RequestMapping("/queryUsers")
	public List<User> queryUsers(@RequestParam String name) {
		List<User> list= new ArrayList<>();
		list.add(new User(1,name+"11"));
		list.add(new User(2,name+"22"));
		return list;
	}
	
	@GetMapping("/feignMVC/{owner}/")//针对@FeignClient中加	configuration= {config.FooConfiguration.class},/
	public String feignMVC(HttpServletRequest request,@PathVariable("owner") String owner)
	{
		String header=null;
		for(Enumeration<String> enumerate=request.getHeaderNames();enumerate.hasMoreElements();  header=enumerate.nextElement())
		{
			if(header!=null)
			{ 
				String val=request.getHeader(header);
				System.out.println("header name="+header+",val="+val); 
			} 
		}

		return "hello "+owner+",i am from port:" +port;
	}
	
	@Autowired
	private EurekaClient eurekaClient;
	 //启动两个 只返回一个
	//http://127.0.0.1:8762/services 
	//http://127.0.0.1:8763/services 
	@RequestMapping("/services") 
	public String serviceUrl() {
    	//SERVICE-HI要大写
    	InstanceInfo instance = eurekaClient.getNextServerFromEureka("SERVICE-HI", false);//只取第一个
    	return instance.getHomePageUrl() ; 
	}
	
	@Autowired
	private DiscoveryClient discoveryClient;
	//请求  http://localhost:8762/services2 启动两个 返回两个
	//http://127.0.0.1:8763/services2
    @GetMapping("/services2")
    public List<String> serviceUrl2() {
        List<ServiceInstance> list = discoveryClient.getInstances("SERVICE-HI");
        List<String> services = new ArrayList<>();
        if (list != null && list.size() > 0 ) {
            list.forEach(serviceInstance -> {
                services.add(serviceInstance.getUri().toString());
            });
        }
        return services;
    }
}
