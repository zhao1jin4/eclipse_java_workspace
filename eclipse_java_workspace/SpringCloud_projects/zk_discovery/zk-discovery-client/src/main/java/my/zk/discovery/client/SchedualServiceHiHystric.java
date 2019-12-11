package my.zk.discovery.client;


import org.springframework.stereotype.Component;

 
@Component
public class SchedualServiceHiHystric implements SchedualServiceHi {
    public String sayHiFromClientOne(String name) {
        return "sorry "+name;
    }
}
