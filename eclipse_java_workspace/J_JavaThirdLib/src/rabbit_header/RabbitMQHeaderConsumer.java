package rabbit_header;
  
import java.io.IOException;
import java.util.Hashtable;  
import java.util.Map;  
  
import org.springframework.amqp.core.ExchangeTypes;  
  
import com.rabbitmq.client.AMQP;  
import com.rabbitmq.client.Channel;  
import com.rabbitmq.client.Connection;  
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;   
  //测试OK,会自动建立exchange,queue,帐号相关吗
public class RabbitMQHeaderConsumer {  
    private final static String EXCHANGE_NAME = "header-exchange";  
    private final static String QUEUE_NAME = "header-queue";  
    
    private final static String mqHost = "127.0.0.1";  
    private final static String mqUser = "zh";  
    private final static String mqPass = "123";  
    public static void main(String[] args) throws Exception {  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost(mqHost); 
        factory.setUsername(mqUser);  
        factory.setPassword(mqPass);  
        factory.setPort(AMQP.PROTOCOL.PORT);  
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
          
        //声明转发器和类型headers  
        //(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, Map<String,Object> arguments)
        channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.HEADERS,false,true,null);  
        
        //String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String,Object> arguments
        channel.queueDeclare(QUEUE_NAME,false, false, true,null);  
          
        Map<String, Object> headers = new Hashtable<String, Object>();  
        headers.put("x-match", "any");//all any 
        headers.put("aaa", "01234"); 
        headers.put("bbb", "56789");  
      
        
        // 为转发器指定队列，设置binding 绑定header键值对  
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME,"", headers);  //不用routeKey
        Consumer consumer = new DefaultConsumer(channel) {
	    	  @Override
	    	  public void handleDelivery(String consumerTag, Envelope envelope,
	    	                             AMQP.BasicProperties properties, byte[] body)
	    	      throws IOException {
	    	    String message = new String(body, "UTF-8");
	    	    System.out.println(" [x] Received '" + message + "'");
	    	  }
	    	}; 
        // 指定接收者，第二个参数为自动应答，无需手动应答  
	    //(String queue, boolean autoAck, Consumer callback
        channel.basicConsume(QUEUE_NAME, true, consumer);   
    }  
}  