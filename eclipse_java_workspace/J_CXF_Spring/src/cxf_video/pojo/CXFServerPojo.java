package cxf_video.pojo;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;

public class CXFServerPojo {
	public static void main(String[] args) {
		HelloWorld helloimpl =new HelloWorldImpl();//都是不加@的	
		ServerFactoryBean  server=new ServerFactoryBean(); //CXF自己的类
		server.setServiceClass(HelloWorld.class);
		server.setAddress("http://localhost:9000/hello");
		server.setServiceBean(helloimpl);
		Server s=server.create();
	}
}
