package rabbitmq;
 

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

 
public class MyRabbitMQGetReceive {
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
	   
 
	    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
		//Get��ʽȡ��Ϣ ��������
	    do 
	    {
			 GetResponse response=channel.basicGet(QUEUE_NAME, false); //����queue, autoAck
			 if(response==null)
			 {
				 Thread.sleep(2000);
				 continue;
			 }
			 String message = new String(response.getBody(), "UTF-8");
			 System.out.println("GET �������� ȡ��Ϣ���Ϊ��"+message);
	 	 
			//���� autoAckΪfalseʱ
	 	    channel.basicAck(response.getEnvelope().getDeliveryTag(),//��Ϣ��ʶ
	 	    		false);//multiple �Ƿ���,�������ʶǰ���һ����ȫ����ΪAck�յ���
	 	    //����Nack(�ɶ��)��֪��(δ�յ�) �� Reject (ֻ��һ��)
	  
			 
			 //---�൱��stack��peek
			 //channel.basicNack(response.getEnvelope().getDeliveryTag(), false, true);//long deliveryTag, boolean multiple, boolean requeue,��Ϊfalse����Ϊdiscarded/dead-lettered
	    }while(true); 
 
//		channel.close(); //������˳���
//		conn.close();
	} 
}
