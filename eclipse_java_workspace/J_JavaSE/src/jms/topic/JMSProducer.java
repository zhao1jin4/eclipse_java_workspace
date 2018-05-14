package jms.topic;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

//import org.apache.activemq.ActiveMQConnection;
//import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息生产者-消息发布者
 * @author Administrator
 *
 */
public class JMSProducer {
 	private static final int SENDNUM=10; // 发送的消息数量
	
	public static void main(String[] args) throws Exception {
		
		ConnectionFactory connectionFactory; // 连接工厂
		Connection connection = null; // 连接
		Session session; // 会话 接受或者发送消息的线程
		Destination destination; // 消息的目的地
		MessageProducer messageProducer; // 消息生产者
		
		//ActiveMQ
//		connectionFactory=new ActiveMQConnectionFactory(=ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);

		//weblogic JMS 通用部分
		String url = "t3://localhost:7001";
		String jndiConnectionFactory = "jms/myFactory";
		String jndiQueue = "jms/myQueue";
		String jndiTopic = "jms/myTopic";
		boolean transacted = false;
		Properties properties = new Properties();
		properties.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
		properties.put(Context.PROVIDER_URL,url);
		Context context = new InitialContext(properties);
		Object lookupFactory = context.lookup(jndiConnectionFactory);
		connectionFactory =(ConnectionFactory)lookupFactory;
	 
		connection=connectionFactory.createConnection(); // 通过连接工厂获取连接
		connection.start(); // 启动连接
		session=connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE); // 创建Session
		// destination=session.createQueue("FirstQueue1"); // 创建消息队列
		destination=session.createTopic("FirstTopic1");
		messageProducer=session.createProducer(destination); // 创建消息生产者
		
		messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT); //topic可持 久化,Producer级的,发的消息都是可持 久化
		
		sendMessage(session, messageProducer); // 发送消息
		session.commit();
		connection.close(); 
		 
	}
	
	/**
	 * 发送消息
	 * @param session
	 * @param messageProducer
	 * @throws Exception
	 */
	public static void sendMessage(Session session,MessageProducer messageProducer)throws Exception{
		for(int i=0;i<JMSProducer.SENDNUM;i++){
			TextMessage message=session.createTextMessage("ActiveMQ 发送的消息"+i);
			message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);//消息级的 持久化
			System.out.println("发送消息："+"ActiveMQ 发布的消息"+i);
			messageProducer.send(message);
		}
	}
}
