package data_jpa_repository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EntityScan
public class MyJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyJpaApplication.class, args);
	}

}
