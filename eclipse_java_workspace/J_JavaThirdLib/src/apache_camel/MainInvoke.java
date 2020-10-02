package apache_camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class MainInvoke {  

	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();  
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				 from("direct:start")
				 .to("class:apache_camel.HelloService?method=sayHello");  
			}
		});
		context.start();
		ProducerTemplate producerTmp=context.createProducerTemplate();
		producerTmp.sendBody("direct:start", "hello everyone");
	}

}
