package org.liukai.tutorial.service;


//必须有接口
public class HelloServiceImpl implements HelloService{

	private String hello="defaultHello";
	
	 public String getHello()
	 {
		 System.out.println("get hello");
		return hello; 
	 }
	 public String setHello(String s)
	 {
		this.hello=s;
		System.out.println("set hello");
		return "hello"; 
	 }
	public void init() {
		System.out.println("==========init");
	}
	public void initAdmin() {
		System.out.println("==========initAdmin");
	}
	public void destroy() {
		System.out.println("==========destroy");
	}
	
	public void other() {
		System.out.println("==========other");
	}
	
}
