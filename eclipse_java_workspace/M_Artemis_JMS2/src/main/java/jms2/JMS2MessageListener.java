package jms2;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

//JMS2.0 提供者必须设置消息属性JMSXDeliveryCount,表示JMS提供者给消费者发送消息的尝试次数,消费者可以根据这个值确认消息是否被重复发送了
public class JMS2MessageListener implements MessageListener {  
   @Override  
   public void onMessage(Message message) {  
      try {  
         int deliveryCount = message.getIntProperty("JMSXDeliveryCount");  
         if (deliveryCount < 10){  
        	 System.out.println(new Date()+" ConsumerDelay get " + ((TextMessage)message).getText());
             //模拟消息处理失败的情形，使得JMS提供者能重发消息  
             throw new RuntimeException("Exception thrown to simulate a bad message");  
         } else {  
             // 消息已经被发送了10次，放弃重发，进行其他的处理  
         }  
      } catch (JMSException e) {  
         throw new RuntimeException(e);  
      }  
   }  
}  