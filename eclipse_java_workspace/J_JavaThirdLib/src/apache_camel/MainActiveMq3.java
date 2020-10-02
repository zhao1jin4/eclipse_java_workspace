package apache_camel;

import java.io.Serializable;
import java.util.Date;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class MainActiveMq3 {  

	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext(); 
		ActiveMQConnectionFactory connectionFactory=new ActiveMQConnectionFactory();
		
		JmsComponent com= JmsComponent.jmsComponentAutoAcknowledge(connectionFactory);
		context.addComponent("jms",com);
		
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				 from("activemq:queue:my_queue") //activeMQÓÐSendTo¹¦ÄÜ
				 .to("seda:end");  
			}
		});
		 context.start();
		
		 ConsumerTemplate consumeTmp=context.createConsumerTemplate();
		String res=consumeTmp.receiveBody("seda:end", String.class);
		System.out.println(res);
	}

} 