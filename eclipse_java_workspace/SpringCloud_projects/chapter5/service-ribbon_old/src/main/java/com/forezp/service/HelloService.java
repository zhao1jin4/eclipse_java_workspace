package com.forezp.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
 
@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError")//断路器(如果几台中一台服务不可用,返回2次固定的错误后,后面就把这台服务器隔离了,就仿问不到这台机器了) 
    public String hiService(String name) {
        //restTemplate 上有 @LoadBalanced 负载均衡客户端
    	return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
        //这里直接SERVICE-HI用名字来连,不区分大小写, 用在方法级别 
    }
    public List<User> queryUsers(@RequestParam String name) {
    	return restTemplate.getForObject("http://SERVICE-HI/queryUsers?name="+name,List.class);
    }
    public String sidecar( ) { 
    		//service-sidecar可小写
    	return restTemplate.getForObject("http://service-sidecar/",String.class);
    }
    public String hiError(String name) {//函数声明要和原函数相同
        return "hi,"+name+",sorry,error!";
    }
}
