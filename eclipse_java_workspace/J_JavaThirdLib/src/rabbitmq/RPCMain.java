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
		 
		//RabbitMQͬһʱ�䷢�������ߵ���Ϣ������һ��
		//�������ܱ�֤�������ڴ�����ĳ�����񣬲�����ȷ����Ϣ��RabbitMQ�Ż����������µ���Ϣ
		//�ڴ�֮���������µ���Ϣ�������ᱻ���͵����������ߣ������е������߶��ڴ���������ô�ͻ�ȴ���
		int prefetchCount = 1;
		channel.basicQos(prefetchCount);//RPC �����Ѷ�  ��������ͨ���ϵ�����������������δȷ����Ϣ��������ĳ̨������Ӧ����

		String EXCHANGE_NAME = "myExtchange";
		String ROUTING_KEY = "routingKey";
		String QUEUE_NAME = "myQueueName";
		//�ᰴ ���Զ�����exchange
		//direct ���� BuiltinExchangeType.DIRECT;
		//channel.exchangeDeclare(EXCHANGE_NAME, "direct", true); // boolean durable  �־û� 
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true);
		
		
		//�Զ�����Queue  ,D=durable
		//String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String,Object> arguments
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);//�ҳ־û�Ҫ�����е�������ͬ,�����Queue�Ѿ����ڲ��ܱ�durable��
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
	        
	        String callbackQueueName = channel.queueDeclare().getQueue(); //�Զ�����Queue,�Զ�ȡ�� ,��AD=AutoDelete,Excl=Exlusive
			System.out.println("callbackQueueName="+callbackQueueName);
			
			String correlationId = UUID.randomUUID().toString();
			AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().replyTo(callbackQueueName)//�ص���Queue
					.correlationId(correlationId)//�������Ĵ�����
					.build();
			
			String message = "Hello World!";
			String EXCHANGE_NAME = "myExtchange";
			String ROUTING_KEY = "routingKey";
			String QUEUE_NAME = "myQueueName";
			channel.exchangeDeclare(EXCHANGE_NAME, "direct", true); //durable �־û�  ,  BuiltinExchangeType.DIRECT 
			//durable true ���û������ ,�������еģ����־û�ǰ���Ȼ����

			 
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

