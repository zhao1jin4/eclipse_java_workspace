package rabbitmq;
 

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.Return;
import com.rabbitmq.client.ReturnCallback;
import com.rabbitmq.client.ReturnListener;
 
public class MyRabbitMQSendReturn {
	 private final static String mqHost = "127.0.0.1";  
	    private final static String mqUser = "zh";  
	    private final static String mqPass = "123";  
	    
	    /** https://rabbitmq.github.io/rabbitmq-java-client/api/current/
	     * rabbitmqctl  add_user zh 123 
	     * rabbitmqctl  set_user_tags  zh  administrator  
	     * rabbitmqctl set_permissions  -p / zh ".*" ".*" ".*"
	     */
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();  
	    factory.setHost(mqHost); 
	    factory.setUsername(mqUser);  
	    factory.setPassword(mqPass);  
	    factory.setPort(AMQP.PROTOCOL.PORT);  
	    System.out.println(factory.getVirtualHost());//默认是/
	    //factory.setVirtualHost("/");
	    
	    String message = "Hello World!";
		String EXCHANGE_NAME = "myExtchange";
		String ROUTING_KEY = "routingKeyx";
		
		
		Connection conn = factory.newConnection(); 
		Channel channel = conn.createChannel(); 
 
		channel.confirmSelect(); 
 
		//对失败的发送, 模拟方式使用错误的 routingKey
		//要求 basicPublish 带 mandatory 或者 immediate 标志
		/*
		channel.addReturnListener(new ReturnListener() {//如要使用lambda语法 使用 ReturnCallback
			@Override
			public void handleReturn(int replyCode,String replyText, String exchange, String routingKey, BasicProperties properties, byte[] body)
					throws IOException {
				System.out.println("ReturnListener replyCode="+replyCode
						+",exchange="+exchange
						+",routingKey="+routingKey
						//+",properties="+properties
						+",body="+new String(body) 
						);
				//只对失败，做重发
			}
		});
		*/
		channel.addReturnListener( new ReturnCallback() {
			@Override
			public void handle(Return returnMessage) {
				System.out.println("ReturnCallback replyCode="+returnMessage.getReplyCode()
				+",returnMessage.toString()="+ ToStringBuilder.reflectionToString(returnMessage));
				//只对失败，做重发
			}
		});
		AMQP.BasicProperties props =MessageProperties.PERSISTENT_TEXT_PLAIN; 
		channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);  
		for(int i=0;i<2;i++)
		{
			//(String exchange, String routingKey, boolean mandatory, boolean immediate, AMQP.BasicProperties props, byte[] body)
			//mandatory为true
			channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,  true,false, props, (message+i).getBytes());
		 	System.out.println(" [x] Sent '" + message +i+ "'");
		} 
		
		System.out.println("sleep 30 seconds begin");
		Thread.sleep(30000); 
		System.out.println("sleep 30 seconds done");
		channel.close(); 
		conn.close();
	}

}
