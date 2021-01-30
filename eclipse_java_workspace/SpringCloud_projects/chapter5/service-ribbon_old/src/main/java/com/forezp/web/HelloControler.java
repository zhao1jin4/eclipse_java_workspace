package com.forezp.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.forezp.service.HelloService;
import com.forezp.service.User;
 
@RestController
public class HelloControler {
    @Autowired
    HelloService helloService;
    
    @RequestMapping(value = "/hi")
    public String hi(@RequestParam String name){
        return helloService.hiService(name);
    }
   // http://localhost:8764/queryUsers?name=lisi 
    @RequestMapping(value = "/queryUsers") 
	public List<User> queryUsers(@RequestParam String name) {
        return helloService.queryUsers(name);
    }
    //http://localhost:8764/sidecar 
    @RequestMapping(value = "/sidecar")
    public String sidecar( ){
        return helloService.sidecar();
    }
    @Autowired
	private LoadBalancerClient loadBalancer; 
    @RequestMapping(value = "/choose")
    public String hi( ){
    	//默认是轮循,已经修改为随机，但SERVICE-HI要用大写
	   ServiceInstance serviceInstance = this.loadBalancer.choose("SERVICE-HI");
	   return serviceInstance.getHost()+":"+serviceInstance.getPort();
    }
   

}
