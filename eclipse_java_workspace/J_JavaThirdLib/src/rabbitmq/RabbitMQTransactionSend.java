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
                 //测试OK
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
            	 //开启事务  
                channel.txSelect();  
                
                //第一个参数是exchangeName(默认情况下代理服务器端是存在一个""名字的exchange的,  
                //因此如果不创建exchange的话我们可以直接将该参数设置成"",如果创建了exchange的话  
                //我们需要将该参数设置成创建的exchange的名字),第二个参数是路由键  
                //(String exchange, String routingKey, AMQP.BasicProperties props, byte[] body)
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, true, MessageProperties.PERSISTENT_BASIC, ("第"+(i+1)+"条消息").getBytes("UTF-8"));
                
                if(i == 1)  
                {  
                    int result = 1/0;  
                }  
                //提交事务  
                channel.txCommit();  
            }
            channel.close();  
            connection.close();
        } catch (Exception e) {  
            try {  
                //catch中回滚操作  
                channel.txRollback();  
            } catch (IOException e1) {  
                e1.printStackTrace();  
            }  
            e.printStackTrace();  
        }  
       
    }  
}  
 