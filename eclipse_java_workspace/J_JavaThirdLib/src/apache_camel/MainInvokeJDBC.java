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
 版本应和camel版本相同
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
		
		//2.24版本到3.0变化 SimpleRegistry类所在包变化，  使用DefaultRegistry类，增加camel-bean-3.3.0.jar
		DefaultRegistry registry=new DefaultRegistry(); //或者 SimpleRegistry
		registry.bind("myDataSource", ds);//2.24版本put方法，3.0版本bind方法
		
		CamelContext context = new DefaultCamelContext(registry);  
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				 from("direct:start")
				 .to("jdbc:myDataSource")
				 .bean(new ResultHandler(),"processResult");  //第二个参数方法名
			}
		});
		context.start();
		ProducerTemplate producerTmp=context.createProducerTemplate();
		producerTmp.sendBody("direct:start", "select table_name,TABLE_ROWS from information_schema.TABLES where table_schema='mydb'");
	}

}
