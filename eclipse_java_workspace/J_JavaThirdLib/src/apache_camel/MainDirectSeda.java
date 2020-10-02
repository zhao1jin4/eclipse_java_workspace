package apache_camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class MainDirectSeda {  

	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext(); 
		
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				 from("direct:start")//camel-direct-3.3.0.jar
				 
				 .process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						String inBody=exchange.getIn().getBody(String.class);
						inBody =inBody +" add in process";
						exchange.getIn().setBody(inBody);//getOut()过时了
					}
				 })
				 
				 .to("seda:end"); //camel-seda-3.3.0.jar   seda: 和redirect: 的区别是 这个是异步的，即开新的线程才执行
			}
		});
		
		context.start();
		
		ProducerTemplate producerTmp=context.createProducerTemplate();
		producerTmp.sendBody("direct:start", "hello everyone");
		
		ConsumerTemplate consumeTmp=context.createConsumerTemplate();
		String res=consumeTmp.receiveBody("seda:end", String.class);
		System.out.println(res);
		 
	}

}