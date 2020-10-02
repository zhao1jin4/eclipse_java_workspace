package apache_camel;

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

public class MainActiveMq {  
/*
<!-- ÒÀÀµcamel°æ±¾Îª 2.24      -->
<dependency>
    <groupId>org.apache.activemq</groupId> 
    <artifactId>activemq-camel</artifactId>
    <version>5.15.13</version>
</dependency>
 */
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext(); 
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory();
		
		JmsComponent com= JmsComponent.jmsComponentAutoAcknowledge(connectionFactory);
		context.addComponent("jms",com);
		
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				 from("file:src/apache_camel/from?noop=true")
				 
				 
				 .to("activemq:queue:my_queue");  
			}
		});
		
		while(true)
			context.start();
		 
	}

}