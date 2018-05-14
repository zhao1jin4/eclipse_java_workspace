package rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class RabbitMQTransactionSend {  
    public static void main(String[] args) {  
        
 
          //测试OK
        ConnectionFactory factory = new ConnectionFactory(); 
		factory.setUsername("zh"); 
		factory.setPassword("123"); 
		factory.setVirtualHost("/"); 
		factory.setHost("172.16.35.35"); 
		factory.setPort(5672); 
		
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
                //第一个参数是exchangeName(默认情况下代理服务器端是存在一个""名字的exchange的,  
                //因此如果不创建exchange的话我们可以直接将该参数设置成"",如果创建了exchange的话  
                //我们需要将该参数设置成创建的exchange的名字),第二个参数是路由键  
                //开启事务  
                channel.txSelect();  
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
                //回滚操作  
                channel.txRollback();  
            } catch (IOException e1) {  
                e1.printStackTrace();  
            }  
            e.printStackTrace();  
        }  
       
    }  
}  
 