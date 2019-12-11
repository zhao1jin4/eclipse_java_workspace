package apache_dubbo27_client;


import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

import apache_dubbo27.GreetingService;

public class ClientApplication {
	org.apache.dubbo.remoting.transport.CodecSupport x;
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");

    public static void main(String[] args) {
    	com.alibaba.com.caucho.hessian.io.Hessian2Output x;
        ReferenceConfig<GreetingService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        reference.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        reference.setInterface(GreetingService.class);
        GreetingService service = reference.get();
        String message = service.sayHi("dubbo");
        System.out.println(message);
    }
}