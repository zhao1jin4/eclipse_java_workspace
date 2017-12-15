package spring_rabbitmq.simple;
import java.util.List;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.amqp.core.AmqpTemplate;


public class StartRabbitMQServer {
	
	public static void main(String[] args) {
	//½¨Á¢  com.mj.test µÄ  Queue
		ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("spring_rabbitmq/simple/spring_rabbitmq_server.xml");
		 
	}
}