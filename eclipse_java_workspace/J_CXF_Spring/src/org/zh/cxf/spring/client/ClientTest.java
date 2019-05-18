package org.zh.cxf.spring.client;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zh.cxf.spring.MyWebServcie;
import org.zh.cxf.spring.SpringHelloWorld;
import org.zh.cxf.spring.User;


public final class ClientTest
{
	private static ClassPathXmlApplicationContext context;
	@BeforeClass
	public static void beforeClass()
	{
		context = new ClassPathXmlApplicationContext(new String[] { "org/zh/cxf/spring/client/client-beans.xml" });
	}
	@AfterClass
	public static void afterClass()
	{
		context = null;
	}
	@Test
	public void testSayHiWithSpringConfig() throws Exception
	{
		SpringHelloWorld client = (SpringHelloWorld) context.getBean("client");
		String response = client.sayHi("Joe");
		assertEquals("Hello Joe", response); //OK
		
		User u=new User();
		u.setId(22);
		u.setName("lisi");
		
		User u2=new User();
		u2.setId(333);
		u2.setName("www");
		
		List<User> list=client.sayHitoUser(u,u2);
		System.out.println(list.get(0).getName());
		System.out.println(list.get(1).getName());
	//	assertEquals("Hello [22,lisi][333,www]", client.sayHitoUser(u,u2));//OK
	}
	
	@Test  //Fail
	public void testUser2() throws Exception
	{
//		{
//			
//			HelloWorld client = (HelloWorld) context.getBean("client2");
//			 
//			User u=new User();
//			u.setId(22);
//			u.setName("lisi");
//			
//			User u2=new User();
//			u2.setId(333);
//			u2.setName("www");
//			List<User> list=client.sayHitoUser(u,u2);
//			System.out.println(list.get(0).getName());
//		
//		}
		//-------------
		SpringHelloWorld client = (SpringHelloWorld) context.getBean("jaxClient");
		 
		User u=new User();
		u.setId(22);
		u.setName("lisi");
		
		User u2=new User();
		u2.setId(333);
		u2.setName("www");
		List<User> list=client.sayHitoUser(u,u2);
		System.out.println(list.get(0).getName());
	}
	
	@Test  //part 
	public void testMyService() throws Exception
	{
		{ //OK
			
			MyWebServcie client = (MyWebServcie) context.getBean("jaxMyServiceClient");
			String res=client.test("中国");
			System.out.println(res);
			
			User u2=new User();
			u2.setId(333);
			u2.setName("www");
			User resUser=client.passObject(u2);
			System.out.println(resUser.getName());
			
		}
//		{//Fail
//			MyWebServcie client = (MyWebServcie) context.getBean("myService");
//			String res=client.test("中国");
//			System.out.println(res);
//			
//			User u2=new User();
//			u2.setId(333);
//			u2.setName("www");
//			User resUser=client.passObject(u2);
//			System.out.println(resUser.getName());
//		}
		
	}
}