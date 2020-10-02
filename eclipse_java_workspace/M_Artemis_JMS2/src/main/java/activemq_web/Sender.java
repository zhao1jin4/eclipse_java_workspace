package activemq_web;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;


public class Sender
{
    private Destination theTopic;
    private Destination theQueue;
	private JmsTemplate jmsTemplate;
	public void setJmsTemplate(JmsTemplate jmsTemplate)
	{
		this.jmsTemplate = jmsTemplate;
	}
	public void setTheTopic(Destination theTopic)
    {
        this.theTopic = theTopic;
    }
    public void setTheQueue(Destination theQueue)
    {
        this.theQueue = theQueue;
    }
    public void send(final String text)
	{
        System.out.println("---Send defualt Queue:" + text);
        jmsTemplate.send(new MessageCreator()
        {
            public Message createMessage(Session session) throws JMSException
            {
                return session.createTextMessage(text);
            }
        });
        
		System.out.println("---Send Queue:" + text);
		jmsTemplate.send(theQueue,new MessageCreator()
		{
			public Message createMessage(Session session) throws JMSException
			{
				return session.createTextMessage(text);
			}
		});
		
		  System.out.println("---Send Topic:" + text);
		jmsTemplate.send(theTopic,new MessageCreator()
        {
            public Message createMessage(Session session) throws JMSException
            {
                return session.createTextMessage(text);
            }
        });
		
		Map<String,Object> msg=new HashMap<String,Object> ();
		 msg.put("isSuccess", "true");
		 jmsTemplate.convertAndSend(theTopic,msg);
		 
	}
}
