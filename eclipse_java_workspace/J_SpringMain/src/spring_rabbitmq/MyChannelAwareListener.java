package spring_rabbitmq;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import com.rabbitmq.client.Channel; 

public class MyChannelAwareListener implements ChannelAwareMessageListener {
 
	@Override
	public void onMessage(Message message, Channel channel)throws Exception 
	{

		System.out.println("收到消息"+message);
		
		try {
			Class.forName("xx");
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (ClassNotFoundException ex) {
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);//数据解析错误则消息回到队列
		} catch (IOException e) {
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);//发生IO错误则直接拒绝接收当前消息
		}
	}
	
}
