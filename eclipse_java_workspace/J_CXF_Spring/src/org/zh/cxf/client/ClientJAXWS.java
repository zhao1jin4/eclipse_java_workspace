package org.zh.cxf.client;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.zh.cxf.HelloWorld;
//同samples 下  java_first_jaxws 
public class ClientJAXWS 
{															 
    private static final QName SERVICE_NAME = new QName("http://cxf.zh.org————/",  "HelloSer————");  
    private static final QName PORT_NAME    = new QName("http://cxf.zh.org/", "HelloWorldPort");
    public static void main(String[] args)
    {
		Service service = Service.create(SERVICE_NAME);
		String endpointAddress = "http://localhost:8000/helloWorld";
		service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING, endpointAddress);//测试用http://localhost:8000/helloWorld?wsdl
		HelloWorld hw = service.getPort(HelloWorld.class);
		System.out.println(hw.sayHi("XXX"));
    }
}