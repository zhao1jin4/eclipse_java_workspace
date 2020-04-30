package springboot_dubbo.server_anno;

import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import springboot_dubbo.DemoService;

import java.util.concurrent.CompletableFuture;

@Service //Dubbo的
public class DemoServiceImpl implements DemoService {
    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);
    @Override
    public String sayHello(String name) {
        logger.info("Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }
    @Override
    public CompletableFuture<String> sayHelloAsync(String name) {
        return null;
    }

}
