package jms_weblogic_ok;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class MyQueueSender 
{
	String url = "t3://135.251.218.79:7001";
	String jndiConnectionFactory = "jms/myFactory";
	String jndiQueue = "jms/myQueue";
	String jndiTopic = "jms/myTopic";
	boolean transacted = false;
	Context context =null;
	Object lookupFactory = null;
	public MyQueueSender()
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
	
	public void parent()
	{
		try
		{
			ConnectionFactory factory =(ConnectionFactory)lookupFactory;
			Queue queue = (Queue)context.lookup(jndiQueue);
			Connection connection =factory.createConnection();
			connection.start();
			Session session = connection.createSession(transacted,  Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer  = session.createProducer(queue);
			
			TextMessage textMessage = session.createTextMessage();
			textMessage.clearBody();
			//textMessage.setStringProperty("testKey", "testValue");
			textMessage.setText("MessageProducer's  Message");
			producer.send(textMessage);//OK,weblogic监视Messages Current列+1
			if (transacted)
			{
				session.commit();
			}
			producer.close();
			session.close();
			connection.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void child()
	{
		try
		{
			QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) lookupFactory;
			Queue queue = (Queue)context.lookup(jndiQueue);
			QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
			queueConnection.start();
			QueueSession queueSession = queueConnection.createQueueSession(transacted, Session.AUTO_ACKNOWLEDGE);
			QueueSender queueSender = queueSession.createSender(queue);
			TextMessage textMessage = queueSession.createTextMessage();
			textMessage.clearBody();
			textMessage.setText("QueueSender's Message");
			queueSender.send(textMessage);//OK ,weblogic监视Messages Current列+1
			if (transacted)
			{
				queueSession.commit();
			}
			queueSender.close();
			queueSession.close();
			queueConnection.close();
			
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		new MyQueueSender().child();
		new MyQueueSender().parent();
			
	}
}
