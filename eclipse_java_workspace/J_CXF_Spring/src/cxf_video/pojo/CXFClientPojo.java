package cxf_video.pojo;

import org.apache.cxf.frontend.ClientProxyFactoryBean;

public class CXFClientPojo {
	public static void main(String[] args) {
		ClientProxyFactoryBean factory=new ClientProxyFactoryBean();//CXF自己的类
		factory.setAddress("http://localhost:9000/hello");
		factory.setServiceClass(HelloWorld.class);
		HelloWorld hello=(HelloWorld)factory.create();
		System.out.println(hello.sayHi("王五"));

	}
}
