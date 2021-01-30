package jms2;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQTopic;

 

public class SharedConsumerMain {
	//测试成功,Artemis使用Netty,如有jdk8报Unsafe找不到,不影响用
	public static void main(String[] args) throws Exception {
	 
		//topic上有三条消息x、y、z,A和B能合起来消费x、y、z，即A消费x、z，B消费y，那只能是A和B共享同一个订阅了
			String user = "input";
			String password = "input";
			String url = "tcp://localhost:61616";
			//ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url,user,password);
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic= new ActiveMQTopic("sharedTopic");
			
			 
			MessageConsumer messageConsumer = session.createSharedConsumer(topic, "mySubscription"); // 需要指定共享订阅的名称，以便多个消费者能确定彼此共享的订阅
			//MessageConsumer messageConsumer = session.createSharedDurableConsumer(topic, "mySubscription");  //也支持持久
			
			connection.start();
			
			messageConsumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
					System.out.println("Message received: " +  message);
				}
			});
				
//			Message message = messageConsumer.receive(10000);
//			while (message != null) {
//				System.out.println("Message received: " + ((TextMessage) message).getText());
//				message = messageConsumer.receive(10000);
//			}
				
			for(int i=0;i<100;i++)
				Thread.sleep(1000);
			connection.close();
	}
 
}
