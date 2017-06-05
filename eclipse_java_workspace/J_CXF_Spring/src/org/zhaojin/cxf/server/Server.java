package org.zhaojin.cxf.server;
import javax.xml.ws.Endpoint;

//同samples 下  java_first_jaxws 
public class Server
{
	public static void main(String[] args)
	{
		System.out.println("Starting Server");
		HelloWorldImpl implementor = new HelloWorldImpl();
		String address = "http://localhost:8000/helloWorld";// 网址的 地址　
		Endpoint.publish(address, implementor);
		System.out.println("Server started.");
	}
}
/*
接口和实现类要在同一个包下,因为名称空间,生成的wsdl不能有<wsdl:import location=
 sample 目录下的java_first_jaxws 
*/

