package springboot_dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"springboot_dubbo.client_anno"})
public class SpringBootDubboConsumerMain {
	public static void main(String[] args) throws Exception {
		//http://127.0.0.1:8082/J_SpringBoot_DubboConsumer/client
		SpringApplication.run(SpringBootDubboConsumerMain.class, args);
	}
}
