package cxf_video;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class CXFServerMain {
	public static void main(String[] args) 
	{
		//启动要 cxf-rt-transports-http-jetty-3.0.2.jar
		//woodstox-core-asl-4.4.1.jar,stax2-api-3.1.4.jar
		
		//wsdl2java -p cxf_video.wsdl2java_gen http://localhost:8080/HelloWorld?wsdl
		
		//JaxWsServerFactoryBean 对使用@的对象,java规范标准处理
		JaxWsServerFactoryBean factory=new JaxWsServerFactoryBean ();//CXF自己的类
		factory.setServiceClass(HelloWorld.class);
		factory.setServiceBean(new HelloWorldImpl());//实现
		factory.setAddress("http://localhost:8080/HelloWorld");
		
		factory.getInInterceptors().add(new LoggingInInterceptor());//会打印出收到客户端HTTP的请求信息
		factory.getOutInterceptors().add(new LoggingOutInterceptor());//会打印出发送到客户端HTTP的响应信息
		
		Server server=factory.create();	//CXF自己的类
		//server.start();//可以不写
		//测试用http://localhost:8080/HelloWorld?wsdl
		//只在HelloWorld和HelloWorldImpl前加@WebService
		
		//CXF/bin/有 wsdl2java,java2wsdl 命令
		//wsdl2java http://localhost:8080/HelloWorld?wsdl  ,会有更全的@,如 @RequestWrapper, @ResponseWrapper
		
//		SOAPBinding.Style.DOCUMENT
//		SOAPBinding.Use.LITERAL
		
		
//		JAXB (Java API for XML Binding)
//		JavaObject ->XML 叫 marshal整顿
//		
	}
}
