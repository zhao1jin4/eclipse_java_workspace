package rabbit_header;
import java.util.Date;  
import java.util.Hashtable;  
import java.util.Map;  
  
import org.springframework.amqp.core.ExchangeTypes;  
  
import com.rabbitmq.client.AMQP;  
import com.rabbitmq.client.AMQP.BasicProperties;  
import com.rabbitmq.client.AMQP.BasicProperties.Builder;  
import com.rabbitmq.client.Channel;  
import com.rabbitmq.client.Connection;  
import com.rabbitmq.client.ConnectionFactory;  
//测试OK,会自动建立exchange,queue,帐号相关吗
public class RabbitMQHeaderProducer {  
    private final static String EXCHANGE_NAME = "header-exchange";  
      
  
    public static void main(String[] args) throws Exception {  
        // 创建连接和频道  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("172.16.35.35");  
        // 指定用户 密码  
        factory.setUsername("zh");  
        factory.setPassword("123");  
        // 指定端口  
        factory.setPort(AMQP.PROTOCOL.PORT);  
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
          
        //声明转发器和类型headers
      //(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, Map<String,Object> arguments)
        channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.HEADERS,false,true,null);  
        String message = new Date()+ " : log something";  
          
        Map<String,Object> headers =  new Hashtable<String, Object>();  
        headers.put("aaa", "01234");  
//      headers.put("bbb", "56789"); //for all 
        Builder properties = new BasicProperties.Builder();  
        properties.headers(headers);  
          
        // 指定消息发送到的转发器,绑定键值对headers键值对  
        //(String exchange, String routingKey, AMQP.BasicProperties props, byte[] body)
        channel.basicPublish(EXCHANGE_NAME, "",properties.build(),message.getBytes());  
          
        System.out.println("Sent message :'" + message + "'");  
        channel.close();  
        connection.close();  
    }  
}  