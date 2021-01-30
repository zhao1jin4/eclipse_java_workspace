package my.zk.discovery.client;

import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public  class DemoControllerService {
    private RestTemplate rest;
    private CircuitBreakerFactory cbFactory;//实现类是 Resilience4J
    public DemoControllerService(RestTemplate rest, CircuitBreakerFactory cbFactory) {
        this.rest = rest;
        this.cbFactory = cbFactory;
    }
    public String hi(String name) {
    	//debug实现类是 Resilience4J
        return cbFactory.create("myid")
        		.run(
        			() -> rest.getForObject("http://"+MyController.remoteServiceName+"/hi?name="+name, String.class), 
        			
        			throwable -> {
        				throwable.printStackTrace(); 
        				return "fallback with Resilience4J"; 
        				}
        		 );
    }
}