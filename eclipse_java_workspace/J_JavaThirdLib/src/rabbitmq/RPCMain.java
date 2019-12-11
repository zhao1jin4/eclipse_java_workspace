package rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ExceptionHandler;
import com.rabbitmq.client.TopologyRecoveryException;
import com.rabbitmq.client.impl.StrictExceptionHandler;

public class RPCMain {
	 private final static String mqHost = "127.0.0.1";  
	    private final static String mqUser = "zh";  
	    private final static String mqPass = "123";  
	    
	    
	public static void main(String[] args) throws Exception {
		rpcServer();
		rpcSend();
	}
	public static void rpcServer() throws Exception{
		ConnectionFactory factory = new ConnectionFactory(); 
		factory.setUsername(mqUser); 
		factory.setPassword(mqPass); 
		factory.setHost(mqHost); 
		factory.setPort(AMQP.PROTOCOL.PORT);   
//		factory.setExceptionHandler(new StrictExceptionHandler() {
//			
//			 });
		factory.setVirtualHost("/"); 
		Connection conn = factory.newConnection(); 
		Channel channel = conn.createChannel(); 
		 
		//RabbitMQ同一时间发给消费者的消息不超过一条
		//这样就能保证消费者在处理完某个任务，并发送确认信息后，RabbitMQ才会向它推送新的消息
		//在此之间若是有新的消息话，将会被推送到其它消费者，若所有的消费者都在处理任务，那么就会等待。
		int prefetchCount = 1;
		channel.basicQos(prefetchCount);//RPC 放消费端  允许限制通道上的消费者所保持最大的未确认消息数量，如某台机器反应慢，

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
	    	
	    	    System.out.println(" [rpc-server] Received '" + message + "'");
	    	 
	    	    BasicProperties replyProps = new BasicProperties
                        .Builder()
                        .correlationId(properties.getCorrelationId())
                        .build();
	    	    channel.basicPublish( "", properties.getReplyTo(), replyProps, "hello reply".getBytes("UTF-8"));
	    	  }
	    	};
	   channel.basicConsume(QUEUE_NAME, true,  consumer);
		
	}
	public static void rpcSend() throws Exception{
	    try {
	        ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost(mqHost);
	        factory.setUsername(mqUser);
	        factory.setPassword(mqPass);
	        factory.setPort(AMQP.PROTOCOL.PORT);  
	
	        Connection connection = factory.newConnection();
	        Channel channel = connection.createChannel();
	        
	        String callbackQueueName = channel.queueDeclare().getQueue(); //自动建立Queue,自动取名 ,是AD=AutoDelete,Excl=Exlusive
			System.out.println("callbackQueueName="+callbackQueueName);
			
			String correlationId = UUID.randomUUID().toString();
			AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().replyTo(callbackQueueName)//回调的Queue
					.correlationId(correlationId)//区分是哪次请求
					.build();
			
			String message = "Hello World!";
			String EXCHANGE_NAME = "myExtchange";
			String ROUTING_KEY = "routingKey";
			String QUEUE_NAME = "myQueueName";
			channel.exchangeDeclare(EXCHANGE_NAME, "direct", true); //durable 持久化  ,  BuiltinExchangeType.DIRECT 
			//durable true 如果没有消费 ,重启后还有的，但持久化前是先缓存的

			 
		 	//(String exchange, String routingKey, AMQP.BasicProperties props, byte[] body)
		 	channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,   props, message  .getBytes());
			System.out.println(" [rpc clent] Sent '" + message  + "'");
			 
			 Consumer consumer = new DefaultConsumer(channel) 
			 {
		    	  @Override
		    	  public void handleDelivery(String consumerTag, Envelope envelope,
		    	                             AMQP.BasicProperties properties, byte[] body)  throws IOException
		    	  {
			    	   if(correlationId.equals(properties.getCorrelationId()))
	    			   {
			    		   String message = new String(body, "UTF-8");
					    	
				    	    System.out.println(" [[rpc-clent] Received '" + message + "'");
	    			   }
	    	    
		    	  }
		    };
		   channel.basicConsume(callbackQueueName, true,   consumer);
		   
			Thread.sleep(30*000); 
	       
	        channel.close();
	        connection.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (TimeoutException e) {
	        e.printStackTrace();
	    }
	}
	  
}

