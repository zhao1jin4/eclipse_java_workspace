package spring_rabbitmq.simple;
import java.util.List;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.amqp.core.AmqpTemplate;


public class ClientRabbitMQSend {
	
	public static void main(String[] args) {
		//���Գɹ� 
		ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("spring_rabbitmq/simple/spring_rabbitmq_client.xml");
		AmqpTemplate amqpTemplate =(AmqpTemplate)ctx.getBean("myAmqpTemplate");
		amqpTemplate.convertAndSend("hello", "xxx");//hello��Routing key,��Ӧxml����key="hello"
		System.out.println("������XXX");
		ctx.close();//������أ��Ͳ��˳�������
	}
}