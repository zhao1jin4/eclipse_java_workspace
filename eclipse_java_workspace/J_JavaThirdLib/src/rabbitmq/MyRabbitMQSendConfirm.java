package rabbitmq;
 

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
 
public class MyRabbitMQSendConfirm {
	 private final static String mqHost = "127.0.0.1";  
	    private final static String mqUser = "zh";  
	    private final static String mqPass = "123";  
	    
	public static void main(String[] args) throws Exception{
		  ConnectionFactory factory = new ConnectionFactory();  
	        factory.setHost(mqHost); 
	        factory.setUsername(mqUser);  
	        factory.setPassword(mqPass);  
	        factory.setPort(AMQP.PROTOCOL.PORT);  
	        
		Connection conn = factory.newConnection(); 
		Channel channel = conn.createChannel(); 
 
		channel.confirmSelect();//publish confirm模式  ，只能通道回复了即可发送下一条（异步），事务是同步 阻塞，即回复后才可发下一条消息。
		
		//异步确认，没用？？？
		channel.addConfirmListener(new ConfirmListener() {
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				System.out.println("handleAck: deliveryTag="+deliveryTag+",multiple="+multiple);
			}
			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				System.out.println("handleNack: deliveryTag="+deliveryTag+",multiple="+multiple);
				//消息重发
			}});
		AMQP.BasicProperties props =MessageProperties.PERSISTENT_TEXT_PLAIN; //当durable true时
		
		String message = "Hello World!";
		String EXCHANGE_NAME = "myExtchange";
		String ROUTING_KEY = "routingKey";
		channel.exchangeDeclare(EXCHANGE_NAME, "direct", true); //durable 持久化  ,  BuiltinExchangeType.DIRECT 
		//durable true 如果没有消费 ,重启后还有的，但持久化前是先缓存的

		for(int i=0;i<2;i++)
		{
		 	//(String exchange, String routingKey, AMQP.BasicProperties props, byte[] body)
		 	channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,   props,
		 			(message+i).getBytes());
			System.out.println(" [x] Sent '" + message +i+ "'");
		}
		//同步确认
	 	//if(!channel.waitForConfirms())//只对channel.confirmSelect()后使用，可以发送多个消息后一次confirm
	 	//	System.out.println("  Send message failed !!! '");
	 	
		Thread.sleep(30*000);
		channel.close(); 
		conn.close();
	}

}
