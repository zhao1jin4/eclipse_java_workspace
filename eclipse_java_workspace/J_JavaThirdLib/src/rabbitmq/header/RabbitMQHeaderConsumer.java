package rabbitmq.header;
  
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
  //����OK,���Զ�����exchange,queue,�ʺ������
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
          
        //����ת����������headers  
        //(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, Map<String,Object> arguments)
        channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.HEADERS,false,true,null);  
        
        //String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String,Object> arguments
        channel.queueDeclare(QUEUE_NAME,false, false, true,null);  
          
        Map<String, Object> headers = new Hashtable<String, Object>();  
        headers.put("x-match", "any");//all any 
        headers.put("aaa", "01234"); 
        headers.put("bbb", "56789");  
      
        
        // Ϊת����ָ�����У�����binding ��header��ֵ��  
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME,"", headers);  //����routeKey
        Consumer consumer = new DefaultConsumer(channel) {
	    	  @Override
	    	  public void handleDelivery(String consumerTag, Envelope envelope,
	    	                             AMQP.BasicProperties properties, byte[] body)
	    	      throws IOException {
	    	    String message = new String(body, "UTF-8");
	    	    System.out.println(" [x] Received '" + message + "'");
	    	  }
	    	}; 
        // ָ�������ߣ��ڶ�������Ϊ�Զ�Ӧ�������ֶ�Ӧ��  
	    //(String queue, boolean autoAck, Consumer callback
        channel.basicConsume(QUEUE_NAME, true, consumer);   
    }  
}  