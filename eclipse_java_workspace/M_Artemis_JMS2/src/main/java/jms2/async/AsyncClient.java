package jms2.async;

import java.util.concurrent.CountDownLatch;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class AsyncClient {
	public static void main(String[] args) throws Exception {
		String user = "input";
		String password = "input";
		String url = ActiveMQConnection.DEFAULT_BROKER_URL;  //failover://tcp://localhost:61616
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user,password,url);
		Queue queue= new ActiveMQQueue("asyncQueue");

		
		//asyncSend11(connectionFactory,queue);//fail
		asyncSend20(connectionFactory,queue);
	}
	
	public  static  void asyncSend11(ConnectionFactory connectionFactory, Queue queue) throws Exception {
		try (Connection connection = connectionFactory.createConnection();) {
			Session session = connection.createSession();//报错？？
			MessageProducer messageProducer = session.createProducer(queue);
			TextMessage textMessage = session.createTextMessage("Hello World!");
			CountDownLatch latch = new CountDownLatch(1);
			SampleCompletionListener completionListener = new SampleCompletionListener(latch);
			messageProducer.send(textMessage, completionListener);
			System.out.println("Waiting for a reply...");

			// 做其他事情，不让应用闲等

			latch.await();

			Exception exception = completionListener.getException();
			if (exception == null) {
				System.out.println("Send message successfully.");
			} else {
				System.out.println("Failed to send message. " + exception.getMessage());
			}
		}
	}

	public  static  void asyncSend20(ConnectionFactory connectionFactory, Queue queue) throws Exception {
		try (JMSContext context = connectionFactory.createContext();)//报错？？
		{
			CountDownLatch latch = new CountDownLatch(1);
			SampleCompletionListener completionListener = new SampleCompletionListener(latch);
			context.createProducer().setAsync(completionListener).send(queue, "Hello World!");
			System.out.println("Waiting for a reply...");

			// 做其他事情，不让应用闲等

			latch.await();

			Exception exception = completionListener.getException();
			if (exception == null) {
				System.out.println("Send message successfully.");
			} else {
				System.out.println("Failed to send message. " + exception.getMessage());
			}
		}
	}

	

}
