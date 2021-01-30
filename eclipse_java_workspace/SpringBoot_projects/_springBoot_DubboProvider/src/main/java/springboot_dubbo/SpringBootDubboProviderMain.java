package springboot_dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"springboot_dubbo.server_anno"})
@EnableDubbo(scanBasePackages = "springboot_dubbo.server_anno")
public class SpringBootDubboProviderMain {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootDubboProviderMain.class, args);
	}
}
