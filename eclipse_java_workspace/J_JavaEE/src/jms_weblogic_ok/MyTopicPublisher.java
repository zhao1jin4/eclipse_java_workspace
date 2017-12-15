package jms_weblogic_ok;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;

//no--------------
public class MyTopicPublisher
{

	String url = "t3://135.251.218.79:7001";
	String jndiConnectionFactory = "jms/myFactory";
	String jndiQueue = "jms/myQueue";
	String jndiTopic = "jms/myTopic";
	boolean transacted = false;
	Context context =null;
	Object lookupFactory = null;
	public MyTopicPublisher()
	{
		Properties properties = new Properties();
		properties.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
		properties.put(Context.PROVIDER_URL,url);
		try
		{
			context = new InitialContext(properties);
			lookupFactory = context.lookup(jndiConnectionFactory);
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public  void parent()
	{
		
		try{	
			ConnectionFactory connectionFactory = (ConnectionFactory) lookupFactory;
			Topic topic = (Topic)context.lookup(jndiTopic);
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
	
			MessageProducer producer = session.createProducer(topic); 
			producer.setDeliveryMode(DeliveryMode.PERSISTENT); //设置保存消息 
			connection.start(); //设置完了后，才连接  
			
			TextMessage msg=session.createTextMessage();
			msg.clearBody();
			msg.setText("Test Message!!!");
			producer.send(msg);
			
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public  void child()
	{
		try
		{
			TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) lookupFactory;
			Topic topic = (Topic)context.lookup(jndiTopic);
			TopicConnection topicConnection = topicConnectionFactory.createTopicConnection();
			TopicSession topicSession = topicConnection.createTopicSession(transacted, Session.AUTO_ACKNOWLEDGE);
			TopicPublisher topicPublisher= topicSession.createPublisher(topic);
			//topicPublisher.setDeliveryMode(DeliveryMode.PERSISTENT);//设置保存消息 
			topicConnection.start();
			
			TextMessage textMessage=topicSession.createTextMessage();
			textMessage.setText("topicPublisher's Message");
			topicPublisher.publish(textMessage);
			
			topicPublisher.close();
			topicSession.close();     
			topicConnection.close();     
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		//new MyTopicPublisher().child();///online and offline OK
		new MyTopicPublisher().parent();//online and offline OK
	}

}
