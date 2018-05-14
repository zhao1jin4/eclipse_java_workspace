package spring_rabbitmq.simple;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class MyLitener implements  MessageListener{
	@Override
	public void onMessage(Message message) {
		System.out.println("收到数据 body :" + new String(message.getBody()));
		System.out.println("收到数据 ReceivedRoutingKey :" + message.getMessageProperties().getReceivedRoutingKey() );
		System.out.println("收到数据 ReceivedExchange :" + message.getMessageProperties().getReceivedExchange());
	}
}