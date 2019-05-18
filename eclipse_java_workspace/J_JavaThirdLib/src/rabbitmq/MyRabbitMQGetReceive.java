package rabbitmq;
 

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

 
public class MyRabbitMQGetReceive {
	//rabbitmqctl  add_user zh 123 , 还要在界面中配置 Admin-> Virtual Hosts 配置/ 虚拟机的用户仿问权限
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
	
 	    
		//会按 名自动建立exchange
		//direct 可用 BuiltinExchangeType.DIRECT;
		//channel.exchangeDeclare(EXCHANGE_NAME, "direct", true); // boolean durable  持久化 
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true);
		
		
		//自动建立Queue  ,D=durable
		//String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String,Object> arguments
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);//且持久化要和现有的配置相同,即如果Queue已经存在不能变durable了
	   
 
	    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
		//Get方式取消息 立即返回
	    do 
	    {
			 GetResponse response=channel.basicGet(QUEUE_NAME, false); //参数queue, autoAck
			 if(response==null)
			 {
				 Thread.sleep(2000);
				 continue;
			 }
			 String message = new String(response.getBody(), "UTF-8");
			 System.out.println("GET 立即返回 取消息结果为："+message);
	 	 
			//对于 autoAck为false时
	 	    channel.basicAck(response.getEnvelope().getDeliveryTag(),//消息标识
	 	    		false);//multiple 是否多个,即这个标识前面的一次性全部认为Ack收到了
	 	    //还有Nack(可多个)不知道(未收到) 和 Reject (只可一个)
	  
			 
			 //---相当于stack的peek
			 //channel.basicNack(response.getEnvelope().getDeliveryTag(), false, true);//long deliveryTag, boolean multiple, boolean requeue,如为false变以为discarded/dead-lettered
	    }while(true); 
 
//		channel.close(); //服务就退出了
//		conn.close();
	} 
}
