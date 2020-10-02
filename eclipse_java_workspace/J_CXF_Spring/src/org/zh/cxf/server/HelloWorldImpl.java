package org.zh.cxf.server;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.zh.cxf.HelloWorld;


//@WebService(endpointInterface = "org.zh.cxf.HelloWorld", serviceName = "HelloSer")
public class HelloWorldImpl implements HelloWorld
{
	public String sayHi(String text) // @WebParam(name = "textSer")
	{
		return "Hello " + text;
	}
}