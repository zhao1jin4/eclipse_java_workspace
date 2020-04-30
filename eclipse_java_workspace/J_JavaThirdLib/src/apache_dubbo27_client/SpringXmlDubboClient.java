package apache_dubbo27_client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import apache_dubbo27.GreetingService;

public class SpringXmlDubboClient {
    /**
     * In order to make sure multicast registry works, need to specify '-Djava.net.preferIPv4Stack=true' before
     * launch the application
     */
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("apache_dubbo27_client/dubbo-consumer.xml");
        context.start();
        GreetingService demoService = context.getBean("greetingService", GreetingService.class);
        //CompletableFuture<String> hello = demoService.sayHelloAsync("world");
//        System.out.println("result: " + hello.get());  
        String  hello = demoService.sayHi("world");
        System.out.println("result: " + hello);  
        
    }
}
