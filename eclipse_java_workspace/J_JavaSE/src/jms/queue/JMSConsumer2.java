package jms.queue;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

//import org.apache.activemq.ActiveMQConnection;
//import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * ��Ϣ������
 * @author Administrator
 *
 */
public class JMSConsumer2 {
 
	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory; // ���ӹ���
		Connection connection = null; // ����
		Session session; // �Ự ���ܻ��߷�����Ϣ���߳�
		Destination destination; // ��Ϣ��Ŀ�ĵ�
		MessageConsumer messageConsumer; // ��Ϣ��������
		
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
		
		 
		connection=connectionFactory.createConnection();  // ͨ�����ӹ�����ȡ����
		connection.start(); // ��������
		session=connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE); // ����Session
		destination=session.createQueue("FirstQueue1");  // �������ӵ���Ϣ����
		messageConsumer=session.createConsumer(destination); // ������Ϣ������
		messageConsumer.setMessageListener(new Listener()); // ע����Ϣ����
	 
	}
}
