package activemq_main;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
public class testActiveMQ 
{
    public static void main(String[] args) throws Exception 
    {
        // apache-activemq-5.11.1\bin\activemq.bat start 来启动
        //ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
        ActiveMQConnectionFactory factory =new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = factory.createConnection();
        connection.start();
        //创建一个Topic
        Topic topic= new ActiveMQTopic("testTopic");//动态建立 
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //注册消费者1
        MessageConsumer comsumer1 = session.createConsumer(topic);
        comsumer1.setMessageListener(new MessageListener(){
            public void onMessage(Message m) {
                try {
                    System.out.println("Consumer1 get " + ((TextMessage)m).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
       
        //注册消费者2
        MessageConsumer comsumer2 = session.createConsumer(topic);
        comsumer2.setMessageListener(new MessageListener(){
            public void onMessage(Message m) {
                try {
                    System.out.println("Consumer2 get " + ((TextMessage)m).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
           
        });
       
        //创建一个生产者，然后发送多个消息。
        MessageProducer producer = session.createProducer(topic);
        for(int i=0; i<10; i++){
            producer.send(session.createTextMessage("Message:" + i));
        }
    }

}