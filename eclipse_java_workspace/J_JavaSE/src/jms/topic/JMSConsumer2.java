package jms.topic;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;

//import org.apache.activemq.ActiveMQConnection;
//import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息消费者-消息订阅者二
 * @author Administrator
 *
 */
public class JMSConsumer2 {
 
	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory; // 连接工厂
		Connection connection = null; // 连接
		Session session; // 会话 接受或者发送消息的线程
		Destination destination; // 消息的目的地
		MessageConsumer messageConsumer; // 消息的消费者
		
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
	 
		connection=connectionFactory.createConnection();  // 通过连接工厂获取连接
		connection.start(); // 启动连接
		session=connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE); // 创建Session
		// destination=session.createQueue("FirstQueue1");  // 创建连接的消息队列
		destination=session.createTopic("FirstTopic1");
//		TopicSubscriber topicSubscriber=topicSession.createDurableSubscriber(topic, "my-sub-name");//第二个参数是唯一标识这个TopicSubscriber (java 进程)的名字,对应于PERSISTENT的topic
		
		messageConsumer=session.createConsumer(destination); // 创建消息消费者
		messageConsumer.setMessageListener(new Listener2()); // 注册消息监听
	 
	}
}
