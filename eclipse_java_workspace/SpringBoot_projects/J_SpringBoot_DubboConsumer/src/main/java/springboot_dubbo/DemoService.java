package springboot_dubbo;

import java.util.concurrent.CompletableFuture;

public interface DemoService { 
    public String sayHello(String name);
 
    public CompletableFuture<String> sayHelloAsync(String name) ;

}