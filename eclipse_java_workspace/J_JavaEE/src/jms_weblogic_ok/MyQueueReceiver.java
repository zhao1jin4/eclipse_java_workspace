package jms_weblogic_ok;

import java.util.Enumeration;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
public class MyQueueReceiver 
{
	
	String url = "t3://135.251.218.79:7001";
	String jndiConnectionFactory = "jms/myFactory";
	String jndiQueue = "jms/myQueue";
	String jndiTopic = "jms/myTopic";
	boolean transacted = false;
	Context context =null;
	Object lookupFactory = null;
	public MyQueueReceiver()
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
			Object obj = context.lookup(jndiQueue);
			Queue queue = (Queue) obj;
	        ConnectionFactory factory =(ConnectionFactory)lookupFactory;
			Connection connection =factory.createConnection();
			connection.start();
			Session session = connection.createSession(transacted,  Session.AUTO_ACKNOWLEDGE);
			MessageConsumer consumer  = session.createConsumer(queue);
			
			//---
//			Message tmpMsg=consumer.receiveNoWait();//OK
//			System.out.println("MessageConsumer get is:"+ tmpMsg);
			//---
			consumer.setMessageListener(new MessageListener(){		
				public void onMessage(Message message) {
					if (message instanceof TextMessage)
					{
						TextMessage textMessage = (TextMessage) message;
						try
						{
							System.out.println("MessageListener get is:"+ textMessage.getText());
						}catch (JMSException e)
						{
							e.printStackTrace();
						}
					}
				}});
			MyQueueReceiver msgRcvr = new MyQueueReceiver();
			synchronized(msgRcvr){ msgRcvr.wait(100000);}  
			//------
			
			if (transacted)
			{
				session.commit();
			}
			consumer.close();
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
			Object obj = context.lookup(jndiQueue);
			Queue queue = (Queue) obj;
			QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
			queueConnection.start();
			QueueSession queueSession = queueConnection.createQueueSession(transacted,  Session.AUTO_ACKNOWLEDGE);
			QueueReceiver queueReceiver = queueSession.createReceiver(queue);
			
			QueueBrowser browser = queueSession.createBrowser(queue);//只看不取 OK
			Enumeration msgs = browser.getEnumeration();
			 while (msgs.hasMoreElements()) 
			 {
				 TextMessage msg = (TextMessage)msgs.nextElement();
			    System.out.println("QueueBrowser get is: " + msg.getText());
			 }
			//--------
//			TextMessage textMessage=(TextMessage)queueReceiver.receive();//会阻塞 ,只读一个继续,可while,OK
//			System.out.println("QueueReceiver get is:"+ textMessage.getText());
			//--------
			 queueReceiver.setMessageListener(new MessageListener(){		//异步 OK, 会读所有的
				public void onMessage(Message message) {
					if (message instanceof TextMessage)
					{
						TextMessage textMessage = (TextMessage) message;
						try
						{
							System.out.println("MessageListener get is:"+ textMessage.getText());
						}catch (JMSException e)
						{
							e.printStackTrace();
						}
					}
				}});
			MyQueueReceiver msgRcvr = new MyQueueReceiver();
			synchronized(msgRcvr){ msgRcvr.wait(100000);}  
			//------
	         queueReceiver.close();     
	         queueSession.close();     
	         queueConnection.close();  
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
			new MyQueueReceiver().parent();
			//new MyQueueReceiver().child();
	}
}
