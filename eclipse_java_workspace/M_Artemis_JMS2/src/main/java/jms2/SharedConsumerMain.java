package jms2;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

public class SharedConsumerMain {

	public static void main(String[] args) throws Exception {
	 
		jms2SharedMsg();//fail
	}

	public static  void  jms2SharedMsg() throws Exception{
		//topic上有三条消息x、y、z,A和B能合起来消费x、y、z，即A消费x、z，B消费y，那只能是A和B共享同一个订阅了
		String user = "input";
		String password = "input";
		String url = ActiveMQConnection.DEFAULT_BROKER_URL;  //failover://tcp://localhost:61616
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user,password,url);
		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic= new ActiveMQTopic("sharedTopic");
		
		//createSharedConsumer 报错
		MessageConsumer messageConsumer = session.createSharedConsumer(topic, "mySubscription"); // 需要指定共享订阅的名称，以便多个消费者能确定彼此共享的订阅
		//MessageConsumer messageConsumer = session.createSharedDurableConsumer(topic, "mySubscription");  //也支持持久
		
		connection.start();
		Message message = messageConsumer.receive(10000);
		while (message != null) {
			System.out.println("Message received: " + ((TextMessage) message).getText());
			message = messageConsumer.receive(10000);
		}
		connection.close();
	}
}
