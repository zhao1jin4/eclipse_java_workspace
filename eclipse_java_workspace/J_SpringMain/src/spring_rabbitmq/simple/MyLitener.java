package spring_rabbitmq.simple;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class MyLitener implements  MessageListener{
	@Override
	public void onMessage(Message message) {
		System.out.println("�յ����� data :" + message.getBody());
	}
}