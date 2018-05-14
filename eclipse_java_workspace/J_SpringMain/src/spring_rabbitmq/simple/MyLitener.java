package spring_rabbitmq.simple;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class MyLitener implements  MessageListener{
	@Override
	public void onMessage(Message message) {
		System.out.println("�յ����� body :" + new String(message.getBody()));
		System.out.println("�յ����� ReceivedRoutingKey :" + message.getMessageProperties().getReceivedRoutingKey() );
		System.out.println("�յ����� ReceivedExchange :" + message.getMessageProperties().getReceivedExchange());
	}
}