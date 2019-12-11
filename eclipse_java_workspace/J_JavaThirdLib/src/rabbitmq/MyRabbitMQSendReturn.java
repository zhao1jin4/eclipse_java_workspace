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
	    System.out.println(factory.getVirtualHost());//Ĭ����/
	    //factory.setVirtualHost("/");
	    
	    String message = "Hello World!";
		String EXCHANGE_NAME = "myExtchange";
		String ROUTING_KEY = "routingKeyx";
		
		
		Connection conn = factory.newConnection(); 
		Channel channel = conn.createChannel(); 
 
		channel.confirmSelect(); 
 
		//��ʧ�ܵķ���, ģ�ⷽʽʹ�ô���� routingKey
		//Ҫ�� basicPublish �� mandatory ���� immediate ��־
		/*
		channel.addReturnListener(new ReturnListener() {//��Ҫʹ��lambda�﷨ ʹ�� ReturnCallback
			@Override
			public void handleReturn(int replyCode,String replyText, String exchange, String routingKey, BasicProperties properties, byte[] body)
					throws IOException {
				System.out.println("ReturnListener replyCode="+replyCode
						+",exchange="+exchange
						+",routingKey="+routingKey
						//+",properties="+properties
						+",body="+new String(body) 
						);
				//ֻ��ʧ�ܣ����ط�
			}
		});
		*/
		channel.addReturnListener( new ReturnCallback() {
			@Override
			public void handle(Return returnMessage) {
				System.out.println("ReturnCallback replyCode="+returnMessage.getReplyCode()
				+",returnMessage.toString()="+ ToStringBuilder.reflectionToString(returnMessage));
				//ֻ��ʧ�ܣ����ط�
			}
		});
		AMQP.BasicProperties props =MessageProperties.PERSISTENT_TEXT_PLAIN; 
		channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);  
		for(int i=0;i<2;i++)
		{
			//(String exchange, String routingKey, boolean mandatory, boolean immediate, AMQP.BasicProperties props, byte[] body)
			//mandatoryΪtrue
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
