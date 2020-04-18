package rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication 
public class RabbitAmqpSpringBoot { 
	//测试成功 
    public static void main(String[] args) throws Exception {
        SpringApplication.run(RabbitAmqpSpringBoot.class, args);
    }
}