package rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class DeadLetterExchangeMain {
	 private final static String mqHost = "127.0.0.1";  
	    private final static String mqUser = "zh";  
	    private final static String mqPass = "123";  
	    
	    
	public static void main(String[] args) throws InterruptedException {
	    sendTTLMessage();//消息级别设置TTL
	    TimeUnit.SECONDS.sleep(5);
	    consumeTTLMessage();//队列级别设置TTL
	}
	
	public static void sendTTLMessage(){
	    try {
	        ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost(mqHost);
	        factory.setUsername(mqUser);
	        factory.setPassword(mqPass);
	        factory.setPort(AMQP.PROTOCOL.PORT);  
	
	        Connection connection = factory.newConnection();
	        Channel channel = connection.createChannel();
	        //消息设置TTL
	        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
	        builder.deliveryMode(2);//持久化
	        builder.expiration("6000");
	        AMQP.BasicProperties  properties = builder.build();
	       
	        boolean mandatory=true;
	        //要先建立  exchangeNameTTL
	        channel.basicPublish("exchangeNameTTL","routingKeyTTL",mandatory,properties,"ttlTestMessage".getBytes());
	
	        channel.close();
	        connection.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (TimeoutException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void consumeTTLMessage(){
	    try {
	        ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost(mqHost);
	        factory.setUsername(mqUser);
	        factory.setPassword(mqPass);
	        factory.setPort(AMQP.PROTOCOL.PORT);  
	
	        Connection connection = factory.newConnection();
	        Channel channel = connection.createChannel();
	        Map<String, Object>  argss = new HashMap<String, Object>();
	        argss.put("vhost", "/");
	        argss.put("username","root");
	        argss.put("password", "root");
	        argss.put("x-message-ttl",6000); //超过指定时时间如没有消费就不能消费了
	        //队列设置TTL
			//不设置TTL,则表示此消息不会过期
			//如果将TTL设置为0，则表示除非此时可以直接将消息投递到消费者,否则该消息会被立即丢
	        channel.queueDeclare("queueNameTTL", true, false, true, argss); //durable, exclusive, autoDelete 
	        
	        Consumer consumer = new DefaultConsumer(channel) {
	        	  @Override
		    	  public void handleDelivery(String consumerTag, Envelope envelope,
		    	                             AMQP.BasicProperties properties, byte[] body)
		    	      throws IOException {
		    	    String message = new String(body, "UTF-8");
		    	    System.out.println(" [x] Received '" + message + "'");
		    	  }
	        };
	       
	
	        channel.close();
	        connection.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (TimeoutException e) {
	        e.printStackTrace();
	    }
	}
}

