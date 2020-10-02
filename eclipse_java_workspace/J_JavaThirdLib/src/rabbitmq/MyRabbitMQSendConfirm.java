package rabbitmq;
 

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

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
 
public class MyRabbitMQSendConfirm {
	 private final static String mqHost = "127.0.0.1";  
	    private final static String mqUser = "zh";  
	    private final static String mqPass = "123";  
	    
	    /**
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
		String ROUTING_KEY = "routingKey";
		
		
		Connection conn = factory.newConnection(); 
		Channel channel = conn.createChannel(); 
 
		//publish confirmģʽ  ��ֻ��ͨ���ظ��˼��ɷ�����һ����Basic.Publish,Basic.Ack����������(Basic.Publish,Tx.Commit,Tx.Commit.OK)�ٷ�һ��ָ�� �����Ϣ��Ҫ�־û������ڴ���̺�ظ�
		//publish confirmģʽ  ��ֻ��ͨ���ظ��˼��ɷ�����һ�����첽����������ͬ�� ���������ظ���ſɷ���һ����Ϣ��
		channel.confirmSelect();
		
		//�첽ȷ��
		/*
		channel.addConfirmListener(new ConfirmListener() {
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				System.out.println("handleAck: deliveryTag="+deliveryTag+",multiple="+multiple);
			}
			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				System.out.println("handleNack: deliveryTag="+deliveryTag+",multiple="+multiple);
			}});
		*/
		
		AMQP.BasicProperties props =MessageProperties.PERSISTENT_TEXT_PLAIN; //��durable trueʱ
		channel.exchangeDeclare(EXCHANGE_NAME, "direct", true); //durable �־û�  ,  BuiltinExchangeType.DIRECT 
		//durable true ���û������ ,�������еģ����־û�ǰ���Ȼ����
		
		for(int i=0;i<2;i++)
		{
		 	//(String exchange, String routingKey, AMQP.BasicProperties props, byte[] body)
		 	channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,   props,
		 			(message+i).getBytes());
			System.out.println(" [x] Sent '" + message +i+ "'");
		}
		//ͬ��ȷ��
		//ֻ��channel.confirmSelect()��ʹ�ã����Է��Ͷ����Ϣ��һ��confirm
		boolean confirmRes=channel.waitForConfirms();
		System.out.println("confirmRes="+confirmRes);
		
//	 	if(!channel.waitForConfirms())
//	 		System.out.println("  Send message failed !!! '");
		
		System.out.println("sleep 30 seconds begin");
		Thread.sleep(30000); 
		System.out.println("sleep 30 seconds done");
		channel.close(); 
		conn.close();
	}

}
