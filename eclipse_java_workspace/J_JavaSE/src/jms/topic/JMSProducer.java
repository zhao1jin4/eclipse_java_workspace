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
 * ��Ϣ������-��Ϣ������
 * @author Administrator
 *
 */
public class JMSProducer {
 	private static final int SENDNUM=10; // ���͵���Ϣ����
	
	public static void main(String[] args) throws Exception {
		
		ConnectionFactory connectionFactory; // ���ӹ���
		Connection connection = null; // ����
		Session session; // �Ự ���ܻ��߷�����Ϣ���߳�
		Destination destination; // ��Ϣ��Ŀ�ĵ�
		MessageProducer messageProducer; // ��Ϣ������
		
		//ActiveMQ
//		connectionFactory=new ActiveMQConnectionFactory(=ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);

		//weblogic JMS ͨ�ò���
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
	 
		connection=connectionFactory.createConnection(); // ͨ�����ӹ�����ȡ����
		connection.start(); // ��������
		session=connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE); // ����Session
		// destination=session.createQueue("FirstQueue1"); // ������Ϣ����
		destination=session.createTopic("FirstTopic1");
		messageProducer=session.createProducer(destination); // ������Ϣ������
		
		messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT); //topic�ɳ� �û�,Producer����,������Ϣ���ǿɳ� �û�
		
		sendMessage(session, messageProducer); // ������Ϣ
		session.commit();
		connection.close(); 
		 
	}
	
	/**
	 * ������Ϣ
	 * @param session
	 * @param messageProducer
	 * @throws Exception
	 */
	public static void sendMessage(Session session,MessageProducer messageProducer)throws Exception{
		for(int i=0;i<JMSProducer.SENDNUM;i++){
			TextMessage message=session.createTextMessage("ActiveMQ ���͵���Ϣ"+i);
			message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);//��Ϣ���� �־û�
			System.out.println("������Ϣ��"+"ActiveMQ ��������Ϣ"+i);
			messageProducer.send(message);
		}
	}
}
