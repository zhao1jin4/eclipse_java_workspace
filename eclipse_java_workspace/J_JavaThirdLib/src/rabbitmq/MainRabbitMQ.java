package rabbitmq;
 

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
public class MainRabbitMQ {

	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory(); 
		factory.setUsername("zh"); 
		factory.setPassword("123"); 
		factory.setVirtualHost("/"); 
		factory.setHost("127.0.0.1"); 
		factory.setPort(5672); 
		Connection conn = factory.newConnection(); 

		Channel channel = conn.createChannel(); 
		channel.exchangeDeclare("myExtchange", "direct", true); //direct，且持久化，非自动删除
		String queueName = channel.queueDeclare().getQueue(); 
		
//		channel.queueBind("myQueue", "myExtchange", routingKey);
		
		channel.close(); 
		conn.close();
		
		
		Connection conn2=getConnection2();
		conn2.close();
		
	}
	public static Connection getConnection2() throws Exception
	{
		ConnectionFactory factory = new ConnectionFactory(); 
		//factory.setUri("amqp://userName:password@hostName:portNumber/virtualHost"); 
		factory.setUri("amqp://zh:123@127.0.0.1:5672/");
		Connection conn = factory.newConnection(); 
		return conn;
	}

}
