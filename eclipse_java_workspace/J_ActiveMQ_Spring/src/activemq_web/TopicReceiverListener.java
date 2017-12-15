package activemq_web;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class TopicReceiverListener implements MessageListener
{
	public void onMessage(Message message)
	{
		try
		{
			if (message instanceof TextMessage)
			{
				TextMessage text = (TextMessage) message;
				System.out.println(" Receive:" + text.getText());
				
			}else if (message instanceof MapMessage)
			 {
				MapMessage mapMsg=(MapMessage)message;
				System.out.println(" Receive Map Names is:"+ mapMsg.getMapNames()); 
			 }
			
		} catch (JMSException e)
		{
			e.printStackTrace();
		}
	}
}
