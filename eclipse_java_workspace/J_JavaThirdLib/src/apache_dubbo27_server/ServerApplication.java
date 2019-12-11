package apache_dubbo27_server;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import apache_dubbo27.GreetingService;

import java.util.concurrent.CountDownLatch;

public class ServerApplication {
	org.apache.dubbo.remoting.transport.CodecSupport x;
	/*
	 <dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo-config-spring</artifactId>
    <version>2.7.4.1</version>
</dependency>
 <dependency>
   <groupId>org.apache.dubbo</groupId>
   <artifactId>dubbo-registry-zookeeper</artifactId>
   <version>2.7.4.1</version>
</dependency>

<dependency>
   <groupId>org.apache.dubbo</groupId>
   <artifactId>dubbo-rpc-dubbo</artifactId>
   <version>2.7.4.1</version>
  </dependency>
  
  <dependency>
   <groupId>org.apache.dubbo</groupId>
   <artifactId>dubbo-remoting-netty4</artifactId>
   <version>2.7.4.1</version>
  </dependency>
  
  <dependency>
   <groupId>org.apache.dubbo</groupId>
   <artifactId>dubbo-serialization-hessian2</artifactId>
   <version>2.7.4.1</version>
  </dependency>
  <dependency>
      <groupId>org.apache.dubbo</groupId>
      <artifactId>dubbo-configcenter-zookeeper</artifactId>
      <version>2.7.4.1</version>
  </dependency>
  <dependency>
   <groupId>org.apache.dubbo</groupId>
   <artifactId>dubbo-serialization-hessian2</artifactId>
   <version>2.7.4.1</version>
  </dependency>
  
	 如使用spring.xml 不能使用 dubbo-2.7.4.1.jar (xml名称空间为alibabatech)
	 dubbo-config-api-2.7.4.1.jar
	dubbo-common-2.7.4.1.jar
	dubbo-rpc-api-2.7.4.1.jar
		dubbo-rpc-dubbo-2.7.4.1.jar
		dubbo-remoting-api-2.7.4.1.jar
		dubbo-rpc-injvm-2.7.4.1.jar
		dubbo-monitor-api-2.7.4.1.jar
		dubbo-remoting-netty4-2.7.4.1.jar
		
	dubbo-registry-api-2.7.4.1.jar
		dubbo-registry-zookeeper-2.7.4.1.jar
	dubbo-configcenter-api-2.7.4.1.jar
	dubbo-configcenter-zookeeper-2.7.4.1.jar
	dubbo-remoting-zookeeper-2.7.4.1.jar
	dubbo-cluster-2.7.4.1.jar
	 */
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");

    public static void main(String[] args) throws Exception {
        ServiceConfig<GreetingService> service = new ServiceConfig<>();
        service.setApplication(new ApplicationConfig("first-dubbo-provider"));
        service.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        service.setInterface(GreetingService.class);
        service.setRef(new GreetingsServiceImpl());
        service.export();

        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}