package my.zk.discovery.client;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
 
@RestController
public class MyController {

    @Autowired
    SchedualServiceHi schedualServiceHi;
    @Autowired 
    DemoControllerService demoControllerService;
    
    @Autowired
    RestTemplate restTemplate;
    
    public static  String remoteServiceName="testZookeeperApp"; 
    		
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    public String sayHi(@RequestParam String name){
    	 System.out.println(serviceUrl());
        return schedualServiceHi.sayHiFromClientOne(name);
       
    }
    
  //http://127.0.0.1:8083/reset?name=lisi    //ribbon或loadbalancer
    @RequestMapping(value = "/reset",method = RequestMethod.GET)
    public String reset(@RequestParam String name){
    	 System.out.println(serviceUrl());
    	return restTemplate.getForObject("http://"+remoteServiceName+"/hi?name="+name,String.class);
    }
    
    //http://127.0.0.1:8083/loadbalancer?name=lisi  //loadbalancer, Resilience4J
    @RequestMapping(value = "/loadbalancer",method = RequestMethod.GET)
    public String loadbalancer(@RequestParam String name){
    	return demoControllerService.hi(name); 
	}

    
	@Autowired
	private DiscoveryClient discoveryClient;

	//请求  http://localhost:8083/services 显示有多个服务注册上去
    @GetMapping("/services")
    public List<String> serviceUrl() {
        List<ServiceInstance> list = discoveryClient.getInstances(remoteServiceName);
        List<String> services = new ArrayList<>();
        if (list != null && list.size() > 0 ) {
            list.forEach(serviceInstance -> {
                services.add(serviceInstance.getUri().toString());
            });
        }
        return services;
    }
	
}
