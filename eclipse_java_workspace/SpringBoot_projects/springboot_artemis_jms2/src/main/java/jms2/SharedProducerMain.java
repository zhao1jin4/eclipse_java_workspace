package jms2;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.artemis.api.core.QueueConfiguration;
import org.apache.activemq.artemis.api.core.RoutingType;
import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.api.core.client.ActiveMQClient;
import org.apache.activemq.artemis.api.core.client.ClientSession;
import org.apache.activemq.artemis.api.core.client.ClientSessionFactory;
import org.apache.activemq.artemis.api.core.client.ServerLocator;
import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.api.jms.JMSFactoryType;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQTopic;

 

public class SharedProducerMain {

	
	public static void main(String[] args) throws Exception {
		//----------------创建 sessionFactory的几种方式 
		ServerLocator locator = ActiveMQClient.createServerLocator("tcp://localhost:61616");
		ClientSessionFactory sessionFactory = locator.createSessionFactory();
		ClientSession session1 = sessionFactory.createSession();
		if(! session1.queueQuery(new SimpleString("example")).isExists())
		{
			//如存在创建报错
			session1.createQueue(new QueueConfiguration("example").setRoutingType(RoutingType.ANYCAST).setDurable(true));
			//RoutingType.ANYCAST 就是QueueRoutingType.MULTICAST 就是Topic
		}
		
		//-------2
		
		Map<String,Object> map=new HashMap<>();
		map.put("host", "127.0.0.1");
		map.put("port", "61616");
		TransportConfiguration transportConfiguration=new TransportConfiguration(NettyConnectorFactory.class.getName(),map);
		ConnectionFactory connectionFactory2 =ActiveMQJMSClient.createConnectionFactoryWithHA(JMSFactoryType.CF, transportConfiguration);
	 
		
		
		//----------------
		
		String user = "input";
		String password = "input";
		String url = "tcp://localhost:61616";
		//ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url,user,password);
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic= new ActiveMQTopic("sharedTopic");//如存在创建不报错
		
		MessageProducer producer=session.createProducer(topic);
		for(int i=0;i<3;i++) 
			producer.send(session.createTextMessage("hello"+i));
		 
		connection.close();
	}
 
}
