package rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class RabbitMQTransactionSend {  
	 private final static String mqHost = "127.0.0.1";  
	    private final static String mqUser = "zh";  
	    private final static String mqPass = "123";  
    public static void main(String[] args) {  
                 //����OK
    	ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost(mqHost); 
        factory.setUsername(mqUser);  
        factory.setPassword(mqPass);  
        factory.setPort(AMQP.PROTOCOL.PORT);  
        
        factory.setVirtualHost("/");  
		
		int count = 3;   
		String EXCHANGE_NAME = "myExtchange";
		String ROUTING_KEY = "routingKey";
		String QUEUE_NAME = "myQueueName"; 
		
		Channel channel = null;  
        try {  
            Connection connection = factory.newConnection();  
            channel = connection.createChannel();  
             
            //(String exchange, String type,  boolean durable, boolean autoDelete, Map<String,Object> arguments)
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);  
            
            //(String queue,  boolean durable,  boolean exclusive,  boolean autoDelete,   Map<String,Object> arguments)
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);  
             
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);  
             
            for(int i = 0;i < count;i++)  
            {  
            	 //��������  
                channel.txSelect();  
                
                //��һ��������exchangeName(Ĭ������´�����������Ǵ���һ��""���ֵ�exchange��,  
                //������������exchange�Ļ����ǿ���ֱ�ӽ��ò������ó�"",���������exchange�Ļ�  
                //������Ҫ���ò������óɴ�����exchange������),�ڶ���������·�ɼ�  
                //(String exchange, String routingKey, AMQP.BasicProperties props, byte[] body)
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, true, MessageProperties.PERSISTENT_BASIC, ("��"+(i+1)+"����Ϣ").getBytes("UTF-8"));
                
                if(i == 1)  
                {  
                    int result = 1/0;  
                }  
                //�ύ����  
                channel.txCommit();  
            }
            channel.close();  
            connection.close();
        } catch (Exception e) {  
            try {  
                //catch�лع�����  
                channel.txRollback();  
            } catch (IOException e1) {  
                e1.printStackTrace();  
            }  
            e.printStackTrace();  
        }  
       
    }  
}  
 