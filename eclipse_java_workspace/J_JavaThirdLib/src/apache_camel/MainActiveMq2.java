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

public class MainActiveMq2 {  

	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext(); 
		ActiveMQConnectionFactory connectionFactory=new ActiveMQConnectionFactory();
		//connectionFactory.setTrustAllPackages(true);
		
		JmsComponent com= JmsComponent.jmsComponentAutoAcknowledge(connectionFactory);
		context.addComponent("jms",com);
		
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				 from("direct:start")
				 .to("activemq:queue:my_queue"); //ativemq会自动创建Queue
			}
		});
		 context.start();
		
		ProducerTemplate producerTmp=context.createProducerTemplate();
		producerTmp.sendBody("direct:start", new Date());//activemq提示不是可信的类
		//>activemq start -Dorg.apache.activemq.SERIALIZABLE_PACKAGES=*
	}

} 