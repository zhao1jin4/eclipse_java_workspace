package quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		//未成功
		SpringApplication.run(Application.class, args);
	}
}
