package com.forezp.service;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by fangzhipeng on 2017/4/6.
 */
@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError")//断路器(如果几台中一台服务不可用,返回2次固定的错误后,后面就把这台服务器隔离了,就仿问不到这台机器了) 
    public String hiService(String name) {
        return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);////这里直接SERVICE-HI用名字来连 , 用在方法级别 
    }

    public String hiError(String name) {
        return "hi,"+name+",sorry,error!";
    }
}
