package spring_rabbitmq;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import com.rabbitmq.client.Channel; 

public class MyChannelAwareListener implements ChannelAwareMessageListener {
 
	@Override
	public void onMessage(Message message, Channel channel)throws Exception 
	{

		System.out.println("�յ���Ϣ"+message);
		
		try {
			Class.forName("xx");
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (ClassNotFoundException ex) {
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);//���ݽ�����������Ϣ�ص�����
		} catch (IOException e) {
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);//����IO������ֱ�Ӿܾ����յ�ǰ��Ϣ
		}
	}
	
}
