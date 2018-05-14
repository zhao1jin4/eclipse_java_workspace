package rabbitmq;
 

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
//测试成功 
public class MyRabbitMQProducerSend {

	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory(); 
		factory.setUsername("zh"); 
		factory.setPassword("123"); 
		factory.setVirtualHost("/"); 
		factory.setHost("172.16.35.35"); 
		factory.setPort(5672); 
		Connection conn = factory.newConnection(); 
		Channel channel = conn.createChannel(); 

		String message = "Hello World!";
		String EXCHANGE_NAME = "myExtchange";
		String ROUTING_KEY = "routingKey";
		channel.exchangeDeclare(EXCHANGE_NAME, "direct", true); //direct，且持久化，非自动删除
		 
	 
	 	//(String exchange, String routingKey, AMQP.BasicProperties props, byte[] body)
	 	channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");
		 
		channel.close(); 
		conn.close();
	}

}
