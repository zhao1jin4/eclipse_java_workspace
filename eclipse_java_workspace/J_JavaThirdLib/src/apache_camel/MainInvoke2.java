package apache_camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultRegistry;
import org.apache.camel.support.SimpleRegistry;

import apache_camel.service.HelloService;

public class MainInvoke2 {  

	public static void main(String[] args) throws Exception {
		HelloService service=new HelloService();
		//2.24�汾��3.0�仯 SimpleRegistry�����ڰ��仯��  ʹ��DefaultRegistry�࣬����camel-bean-3.3.0.jar
		DefaultRegistry registry=new DefaultRegistry(); //���� SimpleRegistry
		registry.bind("helloService", service);//2.24�汾put������3.0�汾bind����
		
		CamelContext context = new DefaultCamelContext(registry);  
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				 from("direct:start")
				 .to("bean:helloService?method=sayHello");  
			}
		});
		context.start();
		ProducerTemplate producerTmp=context.createProducerTemplate();
		producerTmp.sendBody("direct:start", "hello everyone");
	}

}
