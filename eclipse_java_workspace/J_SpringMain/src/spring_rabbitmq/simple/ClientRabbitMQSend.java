package spring_rabbitmq.simple;
import java.util.List;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.amqp.core.AmqpTemplate;


public class ClientRabbitMQSend {
	
	public static void main(String[] args) {
		//测试成功 
		ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("spring_rabbitmq/simple/spring_rabbitmq_client.xml");
		AmqpTemplate amqpTemplate =(AmqpTemplate)ctx.getBean("myAmqpTemplate");
		amqpTemplate.convertAndSend("hello", "xxx");//hello是Routing key,对应xml配置key="hello"
		System.out.println("发送了XXX");
		ctx.close();//如果不关，就不退出？？？
	}
}