package apache_camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class TestMain {
 /*
 
  <dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.apache.camel.springboot</groupId>
            <artifactId>camel-spring-boot-dependencies</artifactId>
            <version>3.3.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>


<dependency>
	<groupId>org.apache.camel.springboot</groupId>
	<artifactId>camel-spring-boot-starter</artifactId>
</dependency> 

自动依赖了 
 <dependency>
	<groupId>org.apache.camel</groupId>
	<artifactId>camel-core</artifactId>
	<version>3.3.0</version>
</dependency>

camel-api-3.3.0.jar
camel-base-3.3.0.jar
camel-core-3.3.0.jar
camel-core-engine-3.3.0.jar
camel-util-3.3.0.jar
camel-support-3.3.0.jar
camel-core-languages-3.3.0.jar
camel-file-3.3.0.jar 用来处理file://协议

	  
  */
	private static final long DURATION_MILIS = 10000;
	private static final String SOURCE_FOLDER = "src/apache_camel/from";
	private static final String DESTINATION_FOLDER = "src/apache_camel/to";

	public static void main(String[] args) throws Exception {
		CamelContext camelContext = new DefaultCamelContext();
//		ProducerTemplate template = camelContext.createProducerTemplate();
//		template.sendBody("endpoint","body");

		
		camelContext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file://" + SOURCE_FOLDER + "?delete=true") //参数参考 https://camel.apache.org/components/latest/file-component.html
				.process(new FileProcessor())
						.to("file://" + DESTINATION_FOLDER);
			}
		});
		camelContext.start();
		Thread.sleep(DURATION_MILIS);
		camelContext.stop();
	}

}