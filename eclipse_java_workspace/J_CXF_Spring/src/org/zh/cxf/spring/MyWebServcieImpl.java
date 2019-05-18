package org.zh.cxf.spring;

//@WebService //不用加
public class MyWebServcieImpl implements MyWebServcie
{
	public String test(String in)
	{
		System.out.println("请求是:"+in);
		return "OK 请求是:"+in;
	}
	
	public User passObject(User in)
	{
		System.out.println("请求是:"+in.getName());
		in.setName("--server name 名字--");
		return in;
	}
}
