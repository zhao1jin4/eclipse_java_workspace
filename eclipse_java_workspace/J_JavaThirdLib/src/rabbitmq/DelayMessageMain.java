package rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class DelayMessageMain {
	 private final static String mqHost = "127.0.0.1";  
	    private final static String mqUser = "zh";  
	    private final static String mqPass = "123";  
	public static void main(String[] args1) throws Exception  {
		 /**

https://www.rabbitmq.com/community-plugins.html
 
https://github.com/rabbitmq/rabbitmq-delayed-message-exchange
rabbitmq_delayed_message_exchange-3.8.0.ez (支持rabbitmq-3.7-3.8.4)文件放 于plugins目录下

rabbitmq-plugins enable rabbitmq_delayed_message_exchange
		  */
		
		ConnectionFactory factory = new ConnectionFactory(); 
		factory.setUsername(mqUser); 
		factory.setPassword(mqPass); 
		factory.setHost(mqHost); 
		factory.setPort(AMQP.PROTOCOL.PORT);   
		  
		factory.setVirtualHost("/"); 
		Connection conn = factory.newConnection(); 
		Channel channel = conn.createChannel(); 
		
		

		// ... elided code ...
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-delayed-type", "direct");
		channel.exchangeDeclare("my-exchange", "x-delayed-message", true, false, args);
		// ... more code ...
		
		
		// ... elided code ...
		byte[] messageBodyBytes = "delayed payload".getBytes("UTF-8");
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("x-delay", 5000);
		AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder().headers(headers);
		channel.basicPublish("my-exchange", "", props.build(), messageBodyBytes);

		byte[] messageBodyBytes2 = "more delayed payload".getBytes("UTF-8");
		Map<String, Object> headers2 = new HashMap<String, Object>();
		headers2.put("x-delay", 1000);
		AMQP.BasicProperties.Builder props2 = new AMQP.BasicProperties.Builder().headers(headers2);
		channel.basicPublish("my-exchange", "", props2.build(), messageBodyBytes2);
		// ... more code ...
	}

}
