package com.forezp.service;
 
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by fangzhipeng on 2017/4/6.
 */

//基于接口的注解 负载均衡客户端
@FeignClient(value = "service-hi", // 同@LoadBalanced 
		fallback = SchedualServiceHiHystric.class) //调用服务全断   不会调用对应的错误实现类的方法???? 
public interface SchedualServiceHi {
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name") String name);
}
