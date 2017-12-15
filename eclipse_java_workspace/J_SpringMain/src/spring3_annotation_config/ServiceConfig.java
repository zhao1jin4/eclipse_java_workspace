package spring3_annotation_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
	@Bean
	public UserService userServcie() {
		return new UserService();
	}
}
