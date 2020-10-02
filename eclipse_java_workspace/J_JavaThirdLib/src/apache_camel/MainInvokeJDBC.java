package apache_camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultRegistry;
import org.apache.camel.support.SimpleRegistry;

import com.mysql.cj.jdbc.MysqlDataSource;

public class MainInvokeJDBC {  
	
/*
 �汾Ӧ��camel�汾��ͬ
	<dependency>
    <groupId>org.apache.camel</groupId>
    <artifactId>camel-jdbc</artifactId>
    <version>2.24.3</version>
</dependency>
*/
	public static void main(String[] args) throws Exception {
		MysqlDataSource ds=new MysqlDataSource();
		ds.setUrl("jdbc:mysql://localhost:3306/mydb");
		ds.setUser("zh");
		ds.setPassword("123");
		
		//2.24�汾��3.0�仯 SimpleRegistry�����ڰ��仯��  ʹ��DefaultRegistry�࣬����camel-bean-3.3.0.jar
		DefaultRegistry registry=new DefaultRegistry(); //���� SimpleRegistry
		registry.bind("myDataSource", ds);//2.24�汾put������3.0�汾bind����
		
		CamelContext context = new DefaultCamelContext(registry);  
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				 from("direct:start")
				 .to("jdbc:myDataSource")
				 .bean(new ResultHandler(),"processResult");  //�ڶ�������������
			}
		});
		context.start();
		ProducerTemplate producerTmp=context.createProducerTemplate();
		producerTmp.sendBody("direct:start", "select table_name,TABLE_ROWS from information_schema.TABLES where table_schema='mydb'");
	}

}
