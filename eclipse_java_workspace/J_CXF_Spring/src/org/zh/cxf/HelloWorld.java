package org.zh.cxf;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.WebParam;
@WebService
public interface HelloWorld
{
	String sayHi(@WebParam(name = "text")String text);
}