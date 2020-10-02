package rabbitmq.header;
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
//����OK,���Զ�����exchange,queue,�ʺ������
public class RabbitMQHeaderProducer {  
    private final static String EXCHANGE_NAME = "header-exchange";  
      
    private final static String mqHost = "127.0.0.1";  
    private final static String mqUser = "zh";  
    private final static String mqPass = "123";  
    public static void main(String[] args) throws Exception {  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost(mqHost); 
        factory.setUsername(mqUser);  
        factory.setPassword(mqPass);  
        // ָ���˿�  
        factory.setPort(AMQP.PROTOCOL.PORT);  
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
          
        //����ת����������headers
      //(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, Map<String,Object> arguments)
        channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.HEADERS,false,true,null);  
        String message = new Date()+ " : log something";  
          
        Map<String,Object> headers =  new Hashtable<String, Object>();  
        headers.put("aaa", "01234");  
//      headers.put("bbb", "56789"); //for all 
        Builder properties = new BasicProperties.Builder();  
        properties.headers(headers);  
          
        // ָ����Ϣ���͵���ת����,�󶨼�ֵ��headers��ֵ��  
        //(String exchange, String routingKey, AMQP.BasicProperties props, byte[] body)
        channel.basicPublish(EXCHANGE_NAME, "",properties.build(),message.getBytes());  
          
        System.out.println("Sent message :'" + message + "'");  
        channel.close();  
        connection.close();  
    }  
}  