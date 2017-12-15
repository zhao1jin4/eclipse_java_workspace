package jms_weblogic_ok;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
public class MyTopicSubscriber
{

	String url = "t3://135.251.218.79:7001";
	String jndiConnectionFactory = "jms/myFactory";
	String jndiQueue = "jms/myQueue";
	String jndiTopic = "jms/myTopic";
	boolean transacted = false;
	Context context =null;
	Object lookupFactory = null;
	public MyTopicSubscriber()
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
		try
		{
			ConnectionFactory connectionFactory = (ConnectionFactory) lookupFactory;
			Topic topic = (Topic)context.lookup(jndiTopic);
			
			Connection connection = connectionFactory.createConnection();
			connection.setClientID("client-name-1"); 
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
			TopicSubscriber  consumer = session.createDurableSubscriber(topic, "my-sub-name-1"); 
			connection.start();
			Message msg=consumer.receive();
			System.out.println("parent Topic get is:"+msg);
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
			//topicConnection.setClientID("client-name");
			TopicSession topicSession = topicConnection.createTopicSession(transacted, Session.AUTO_ACKNOWLEDGE);
			
			//TopicSubscriber topicSubscriber=topicSession.createDurableSubscriber(topic, "my-sub-name"); 
			//会在weblogic的Monitor->Durable Subscribers下建立的,离线也可取消息,之后connection.start();
			TopicSubscriber topicSubscriber= topicSession.createSubscriber(topic);//必须在线可取消息
			topicConnection.start();
			topicSubscriber.setMessageListener(new MessageListener() 
			{
				public void onMessage(Message msg)
				{
					if(msg instanceof TextMessage)
					{
						TextMessage t=(TextMessage)msg;
						try
						{
							System.out.println("child Topic get is:"+t.getText());
						} catch (JMSException e)
						{
							e.printStackTrace();
						}
					}
				}});
			
			MyTopicSubscriber my=new MyTopicSubscriber();
			synchronized(my){my.wait(100000);}    
		   topicSubscriber.close();	 
    	   topicSession.close();     
    	   topicConnection.close();     
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		//new MyTopicSubscriber().child();//online and offline OK	normal
		new MyTopicSubscriber().parent();//online and offline OK  persistent
	}
}
