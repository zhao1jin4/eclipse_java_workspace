package config;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner implements ApplicationRunner  {
	//ApplicationRunner 和 CommandLineRunner 都是在SpringApplication.run( )完成之前 调用
	@Override
	public void run(ApplicationArguments args) throws Exception {
		 System.out.println("===SpringBoot初始化完成，开始做自己的逻辑====");
	}
}
