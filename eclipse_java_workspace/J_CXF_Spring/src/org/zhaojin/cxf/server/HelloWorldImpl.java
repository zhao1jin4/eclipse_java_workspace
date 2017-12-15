package org.zhaojin.cxf.server;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.zhaojin.cxf.HelloWorld;


//@WebService(endpointInterface = "org.zhaojin.cxf.HelloWorld", serviceName = "HelloSer")
public class HelloWorldImpl implements HelloWorld
{
	public String sayHi(String text) // @WebParam(name = "textSer")
	{
		return "Hello " + text;
	}
}