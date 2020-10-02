package org.zh.cxf.spring;


import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface MyWebServcie {
	   public String test(@WebParam(name = "strIn")String in);
	   
	   public User passObject(User in);
}
