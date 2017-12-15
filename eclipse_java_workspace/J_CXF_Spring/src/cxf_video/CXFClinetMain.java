package cxf_video;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class CXFClinetMain {
	
	public static void main(String[] args) throws Exception
	{
		//JaxWsProxyFactoryBean 对使用@的对象,java规范标准处理
		JaxWsProxyFactoryBean factory =new JaxWsProxyFactoryBean();//CXF自己的类
		factory.setAddress("http://localhost:8080/HelloWorld");
		factory.setServiceClass(HelloWorld.class);//接口
		HelloWorld hello=(HelloWorld)factory.create();
		System.out.println(hello.sayHi("李四"));
		
		
	}
}
