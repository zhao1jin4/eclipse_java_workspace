package rabbitmq;
 

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

//测试成功
public class MyRabbitMQConsumerReceive {
	//rabbitmqctl  add_user zh 123 , 还要在界面中配置 Admin-> Virtual Hosts 配置/ 虚拟机的用户仿问权限
    private final static String mqHost = "127.0.0.1";  
    private final static String mqUser = "zh";  
    private final static String mqPass = "123";  
    
    
	public static void main(String[] args) throws Exception{
		
		ConnectionFactory factory = new ConnectionFactory(); 
		factory.setUsername(mqUser); 
		factory.setPassword(mqPass); 
		factory.setHost(mqHost); 
		factory.setPort(AMQP.PROTOCOL.PORT);   
		  
		factory.setVirtualHost("/"); 
		Connection conn = factory.newConnection(); 
		Channel channel = conn.createChannel(); 
		 
		//RabbitMQ同一时间发给消费者的消息不超过一条
		//这样就能保证消费者在处理完某个任务，并发送确认信息后，RabbitMQ才会向它推送新的消息
		//在此之间若是有新的消息话，将会被推送到其它消费者，若所有的消费者都在处理任务，那么就会等待。
		int prefetchCount = 1;
		channel.basicQos(prefetchCount);//放消费端

		
		
		String EXCHANGE_NAME = "myExtchange";
		String ROUTING_KEY = "routingKey";
		String QUEUE_NAME = "myQueueName";
		//会按 名自动建立exchange
		//direct 可用 BuiltinExchangeType.DIRECT;
		//channel.exchangeDeclare(EXCHANGE_NAME, "direct", true); // boolean durable  持久化 
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true);
		
		
		//自动建立Queue  ,D=durable
		//String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String,Object> arguments
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);//且持久化要和现有的配置相同,即如果Queue已经存在不能变durable了
	    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
 
	    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
		
	    Consumer consumer = new DefaultConsumer(channel) {
	    	  @Override
	    	  public void handleDelivery(String consumerTag, Envelope envelope,
	    	                             AMQP.BasicProperties properties, byte[] body)
	    	      throws IOException {
	    	    String message = new String(body, "UTF-8");
	    	
	    	    System.out.println(" [x] Received '" + message + "'");
	    	    
	    	    
	    	    //对于basicConsume  autoAck为false时
	    	    channel.basicAck(envelope.getDeliveryTag(),//消息标识
	    	    		false);//multiple 是否多个,即这个标识前面的一次性全部认为Ack收到了
	    	    //还有Nack(可多个)不知道(未收到) 和 Reject (只可一个)
	    	    
	    	  }
	    	};
	   channel.basicConsume(QUEUE_NAME, false, //boolean autoAck,true收到消息就自动应答,false要手工应答(在消息任务完成后)
			   consumer);
		
 
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
