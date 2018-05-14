package rabbitmq;
 

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

//测试成功
public class MyRabbitMQConsumerReceive {

	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory(); 
		factory.setUsername("zh"); 
		factory.setPassword("123"); 
		factory.setVirtualHost("/"); 
		factory.setHost("172.16.35.35"); 
		factory.setPort(5672); 
		Connection conn = factory.newConnection(); 
		Channel channel = conn.createChannel(); 
		 
		String EXCHANGE_NAME = "myExtchange";
		String ROUTING_KEY = "routingKey";
		String QUEUE_NAME = "myQueueName";
		
		channel.exchangeDeclare(EXCHANGE_NAME, "direct", true); //direct，且持久化，非自动删除
		
		String queueName = channel.queueDeclare().getQueue(); 
		System.out.println("queueName="+queueName);
		
 
		//String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String,Object> arguments
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);//且持久化要和现有的配置相同
	    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
 
	    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
		
	    Consumer consumer = new DefaultConsumer(channel) {
	    	  @Override
	    	  public void handleDelivery(String consumerTag, Envelope envelope,
	    	                             AMQP.BasicProperties properties, byte[] body)
	    	      throws IOException {
	    	    String message = new String(body, "UTF-8");
	    	    System.out.println(" [x] Received '" + message + "'");
	    	  }
	    	};
	   channel.basicConsume(QUEUE_NAME, true, consumer);
		
		
//		channel.close(); //服务就退出了
//		conn.close();
		
		
//		Connection conn2=getConnection2();
//		conn2.close();
		
	}
	public static Connection getConnection2() throws Exception
	{
		ConnectionFactory factory = new ConnectionFactory(); 
		//factory.setUri("amqp://userName:password@hostName:portNumber/virtualHost"); 
		//报错？？？？
		factory.setUri("amqp://zh:123@172.16.35.35:5672//");
		
		Connection conn = factory.newConnection(); 
		return conn;
	}

}
