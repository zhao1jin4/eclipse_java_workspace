package activemq_main;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
public class MainApp 
{
	public static  void testTopic()throws Exception
	{
		  String user = ActiveMQConnection.DEFAULT_USER;
			String password = ActiveMQConnection.DEFAULT_PASSWORD;
		     String url = ActiveMQConnection.DEFAULT_BROKER_URL;  //failover://tcp://localhost:61616
			//ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(user,password,url);//要双击apache-activemq-5.5.1\bin\activemq.bat 来启动
		     ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");//要双击apache-activemq-5.5.1\bin\activemq.bat 来启动
		    // vm://localhost,tcp://localhost:61616
			Connection connection = factory.createConnection();
			connection.start();
			//创建一个Topic
			Topic topic= new ActiveMQTopic("testTopic");//动态建立
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//topic=session.createTopic("dynamicTopic");
			
			//注册消费者1
			MessageConsumer comsumer1 = session.createConsumer(topic);
			comsumer1.setMessageListener(new MessageListener()
			{
				public void onMessage(Message m) {
					try {
						System.out.println("Consumer11111 get " + ((TextMessage)m).getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});
			
			System.out.println("started");

			
			//创建一个生产者，然后发送多个消息。
			MessageProducer producer = session.createProducer(topic);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			for(int i=0; i<10; i++)
			{
				producer.send(session.createTextMessage("TopicMessage:" + i));
			}
			producer.close();
	}
	
	public static  void testQueue()throws Exception
	{
		  String user = ActiveMQConnection.DEFAULT_USER;
		     String password = ActiveMQConnection.DEFAULT_PASSWORD;
		     String url = ActiveMQConnection.DEFAULT_BROKER_URL;  //failover://tcp://localhost:61616
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(user,password,url);//要双击apache-activemq-5.5.1\bin\activemq.bat 来启动
		     //ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");//要双击apache-activemq-5.5.1\bin\activemq.bat 来启动
		    // vm://localhost,tcp://localhost:61616
			Connection connection = factory.createConnection();
			connection.start();
			Queue queue= new ActiveMQQueue("testQueue");
			
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			queue=session.createQueue("dynamicQueue");
			
			//注册消费者1
			MessageConsumer comsumer1 = session.createConsumer(queue);
			comsumer1.setMessageListener(new MessageListener()
			{
				public void onMessage(Message m) {
					try {
						System.out.println("Consumer22222 get " + ((TextMessage)m).getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});
			
			System.out.println("started");

			
			//创建一个生产者，然后发送多个消息。
			MessageProducer producer = session.createProducer(queue);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			for(int i=0; i<10; i++)
			{
				producer.send(session.createTextMessage("QueueMessage:" + i));
			}
			producer.close();
	}
	public static  void testQueueTransaction()throws Exception
	{
		  String user = ActiveMQConnection.DEFAULT_USER;
		     String password = ActiveMQConnection.DEFAULT_PASSWORD;
		     String url = ActiveMQConnection.DEFAULT_BROKER_URL;  //failover://tcp://localhost:61616
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(user,password,url);//要双击apache-activemq-5.5.1\bin\activemq.bat 来启动
		     //ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");//要双击apache-activemq-5.5.1\bin\activemq.bat 来启动
		    // vm://localhost,tcp://localhost:61616
			Connection connection = factory.createConnection();
			connection.start();
			Queue queue= new ActiveMQQueue("testQueue");
			
			//在容器中一个connection只能创建一个活的session,否则异常
			Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE); //boolean transacted, int acknowledgeMode
			// 对不在JTA事务中(如在JTA事务中,参数失效,commit,rollback,也失败,依赖于JTA事务),如transacted为true使用session.rollback();或 session.commit();   acknowledgeMode参数被忽略
			//Session.CLIENT_ACKNOWLEDGE ,Session.AUTO_ACKNOWLEDGE ,Session.DUPS_OK_ACKNOWLEDGE;
			//ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE;ActiveMQ专有的
			
			//session = connection.createSession(Session.SESSION_TRANSACTED);//sessionMode ,其它的同上面
			session.rollback();
			session.commit();//真正投递到中间件，而只有进行session.commit操作之后，消息才会发送到中间件
			
	}
	public static  void testQueueGroup()throws Exception
	{
		//官方  上 Message Groups
		
	}
	public static void main(String[] args) throws Exception
	{
		//testTopic();
		testQueue();
		
		
		testQueueTransaction();
		testQueueGroup();
		
	}
}
