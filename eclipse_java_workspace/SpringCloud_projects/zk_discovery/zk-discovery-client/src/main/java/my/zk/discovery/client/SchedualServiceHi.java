package my.zk.discovery.client;

 
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

 
//基于接口的注解 负载均衡客户端
@FeignClient(value = "testZookeeperApp", // 同@LoadBalanced 
		fallback = SchedualServiceHiHystric.class)  
public interface SchedualServiceHi {
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name") String name);
}
