 package org.zhaojin.cxf.spring;  

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.ResponseWrapper;
  
 
@WebService  
public interface SpringHelloWorld 
 {
	//@ResponseWrapper(localName = "mySayHiResponse", className = "org.zhaojin.cxf.spring.User")
	String sayHi(@WebParam(name = "strText") String text);  //不使用这个？？？
	 
	 public List<User> sayHitoUser(User user,User user2); 
 }  