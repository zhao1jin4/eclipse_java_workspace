package mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class SpringBootMongoDb {

	public static void main(String[] args) {
		   ConfigurableApplicationContext context = SpringApplication.run(SpringBootMongoDb.class, args);
		   MyBean myBean=context.getBean(MyBean.class);
		   myBean.example();
	}

}
