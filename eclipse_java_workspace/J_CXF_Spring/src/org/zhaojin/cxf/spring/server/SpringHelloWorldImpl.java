package org.zhaojin.cxf.spring.server;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.ResponseWrapper;

import org.zhaojin.cxf.spring.SpringHelloWorld;
import org.zhaojin.cxf.spring.User;
 
public class SpringHelloWorldImpl     implements SpringHelloWorld
{
	public String sayHi(   String text) 
	{
		return "Hello " + text;
	}
	 public List<User> sayHitoUser(User user,User user2)
	 {
		 List x=new ArrayList();
		 x.add(user);
		 x.add(user2);
		 
		 return x;
		// return "Hello [" + user.getId()+","+user.getName()+"]["+user2.getId()+","+user2.getName()+"]";
	 }
}