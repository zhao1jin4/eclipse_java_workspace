package rabbitmq_offical;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*
 @Configuration
  class RabbitConfiguration {

	@Bean
	public SimpleRabbitListenerContainerFactory myFactory( SimpleRabbitListenerContainerFactoryConfigurer configurer) {
		SimpleRabbitListenerContainerFactory factory =
				new SimpleRabbitListenerContainerFactory();
//		configurer.configure(factory, connectionFactory);
//		factory.setMessageConverter(myMessageConverter());
		return factory;
	}
}
*/