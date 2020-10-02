package jms2;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

public class  JMSContexDelayRetryMain  {
	public static void main(String[] args) throws Exception {
		//jms2DelaySendAndRetry();//fail
		//sendWithDeliveryDelayClassic();//fail
	}
	public static void sendWithDeliveryDelayClassic() 
	   throws JMSException {

		String user = "input";
		String password = "input";
		String url = ActiveMQConnection.DEFAULT_BROKER_URL;  //failover://tcp://localhost:61616
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user,password,url);
		Queue queue= new ActiveMQQueue("delayQueue");

		
	   // send a message with a delivery delay of 20 seconds
	   try (Connection connection = connectionFactory.createConnection();){
//	      Session session = connection.createSession();//报错？？
		  Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	      MessageProducer messageProducer = session.createProducer(queue);
	      messageProducer.setDeliveryDelay(20000);//报错？？
	      TextMessage textMessage = session.createTextMessage("Hello world");
	      messageProducer.send(textMessage);
	   }
	}
	
	
	public static  void jms2DelaySendAndRetry()
	{
		String user = "input";
		String password = "input";
		String url = ActiveMQConnection.DEFAULT_BROKER_URL;  //failover://tcp://localhost:61616
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user,password,url);
		try (JMSContext context = connectionFactory.createContext();) {//不能创建JMSContext???
			Queue queue= new ActiveMQQueue("delayQueue");
			JMSConsumer comsumer1 =context.createConsumer(queue);
			comsumer1.setMessageListener(new JMS2MessageListener());
			System.out.println(new Date()+" sended mssage");
			context.createProducer().setDeliveryDelay(20000).send(queue, "Hello World!"); // 20000毫秒，也是在发送消息之前设置
		}
	}
}
