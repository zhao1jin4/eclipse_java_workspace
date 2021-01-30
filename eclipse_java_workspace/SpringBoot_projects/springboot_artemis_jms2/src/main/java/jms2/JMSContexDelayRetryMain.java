package jms2;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
 

public class  JMSContexDelayRetryMain  {
	public static void main(String[] args) throws Exception {
		jms2DelaySendAndRetry();//JMSProducer   
		//sendWithDeliveryDelayClassic();// 和 JMS2MessageProducerDelay  一样
	}
	public static void sendWithDeliveryDelayClassic() 
	   throws JMSException, Exception {

		String user = "input";
		String password = "input";
		String url = "tcp://localhost:61616";
//		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url,user,password);
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Queue queue= new ActiveMQQueue("delayQueue");

		
	   // send a message with a delivery delay of 20 seconds
	   try (Connection connection = connectionFactory.createConnection();){
		  connection.start();
		  Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		  
		  session.createConsumer(queue).setMessageListener(new JMS2MessageListener());
		  
	      MessageProducer messageProducer = session.createProducer(queue);
	      //messageProducer.setDeliveryDelay(2000);//MessageProducer 设置
	      TextMessage textMessage = session.createTextMessage("Hello world");
	      textMessage.setJMSDeliveryTime(System.currentTimeMillis()+3000);//消息级设置
	      messageProducer.send(textMessage);
	      
	      for(int i=0;i<100;i++)
			{	
				Thread.sleep(1000);
				System.out.println("wait");
			}
	   }//后面会自动 connection.close() 
		
	}
	
	
	public static  void jms2DelaySendAndRetry() throws Exception
	{
		String user = "input";
		String password = "input";
		String url = "tcp://localhost:61616";
//		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url,user,password);
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		try (JMSContext context = connectionFactory.createContext();) {
			Queue queue= new ActiveMQQueue("delayQueue");
			
			JMSConsumer comsumer1 =context.createConsumer(queue);
			comsumer1.setMessageListener(new JMS2MessageListener());
			
			System.out.println(new Date()+" sended mssage");
			
			TextMessage textMsg=context.createTextMessage("hello");
			textMsg.setJMSDeliveryTime(System.currentTimeMillis()+3000);//消息级设置
			
			JMSProducer jmsProducer=context.createProducer();
//			jmsProducer.setDeliveryDelay(2000);
//			jmsProducer.send(queue, "Hello World!"); //毫秒，JMSProducer设置 ,也是在发送消息之前设置
			
			jmsProducer.send(queue, textMsg); 
			 
		      for(int i=0;i<100;i++)
				{	
					Thread.sleep(1000);
					System.out.println("wait");
				}
		}
	}
}
