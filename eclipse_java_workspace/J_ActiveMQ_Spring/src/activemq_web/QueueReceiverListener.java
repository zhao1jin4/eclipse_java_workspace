package activemq_web;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class QueueReceiverListener implements MessageListener
{
	public void onMessage(Message message)
	{
		if (message instanceof TextMessage)
		{
			TextMessage text = (TextMessage) message;
			try
			{
				System.out.println("Queue Receive:" + text.getText());
			} catch (JMSException e)
			{
				e.printStackTrace();
			}
		}
	}
}
