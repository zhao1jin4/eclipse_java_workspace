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

//���Գɹ�
public class MyRabbitMQConsumerReceive {
	//rabbitmqctl  add_user zh 123 , ��Ҫ�ڽ��������� Admin-> Virtual Hosts ����/ ��������û�����Ȩ��
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
		 
		//RabbitMQͬһʱ�䷢�������ߵ���Ϣ������һ��
		//�������ܱ�֤�������ڴ�����ĳ�����񣬲�����ȷ����Ϣ��RabbitMQ�Ż����������µ���Ϣ
		//�ڴ�֮���������µ���Ϣ�������ᱻ���͵����������ߣ������е������߶��ڴ���������ô�ͻ�ȴ���
		int prefetchCount = 1;
		channel.basicQos(prefetchCount);//�����Ѷ�

		
		
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
	    	
	    	    System.out.println(" [x] Received '" + message + "'");
	    	    
	    	    
	    	    //����basicConsume  autoAckΪfalseʱ
	    	    channel.basicAck(envelope.getDeliveryTag(),//��Ϣ��ʶ
	    	    		false);//multiple �Ƿ���,�������ʶǰ���һ����ȫ����ΪAck�յ���
	    	    //����Nack(�ɶ��)��֪��(δ�յ�) �� Reject (ֻ��һ��)
	    	    
	    	  }
	    	};
	   channel.basicConsume(QUEUE_NAME, false, //boolean autoAck,true�յ���Ϣ���Զ�Ӧ��,falseҪ�ֹ�Ӧ��(����Ϣ������ɺ�)
			   consumer);
		
 
//		channel.close(); //������˳���
//		conn.close();
		
		
//		Connection conn2=getConnection2();
//		conn2.close();
		
	}
	public static Connection getConnection2() throws Exception
	{
		ConnectionFactory factory = new ConnectionFactory(); 
		//factory.setUri("amqp://userName:password@hostName:portNumber/virtualHost"); 
		//����������
		factory.setUri("amqp://zh:123@172.16.35.35:5672//");
		
		Connection conn = factory.newConnection(); 
		return conn;
	}

}
