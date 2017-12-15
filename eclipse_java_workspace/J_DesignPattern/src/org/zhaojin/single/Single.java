package org.zhaojin.single;

import java.awt.Toolkit;

public class Single {
	public static Single s;
	public static Single s1 = new Single();// 为线程安全,饿汉式,但也只有用到这个类时,这个类才会被classloader加载
	public static Single s3;

	private Single() {
	}

	public   static Single getInstance() {
		// return s1;

		/*
		  if(s==null)//可能是线程不安全的,方法体加synchronized,赖汉式
			s=new Single(); 
		  return s;
		 */

		// 双重检查 ,在Java编译器中(老版本)不成立,正确的做法是在方法前加synchronized
		if (s3 == null)
		{
			synchronized (Single.class)//类.class  (double check JDK7,8成立)
			{	
				if (s3 == null) 
					s3 = new Single();
			}
		}
		return s3;
	}

	public static void main(String[] args) {
		new Thread(new Runnable(){
			public void run() {
				Single.getInstance();
			}
		}).start();
		
		Single a = Single.getInstance();
		Single b = Single.getInstance();
		System.out.println(a == b);

		Runtime runtime = Runtime.getRuntime();
		Runtime runtime1 = Runtime.getRuntime();
		System.out.println(runtime == runtime1);

		Toolkit tookit = Toolkit.getDefaultToolkit();// Toolkit是抽象,如继承
		
		Foo f=new Foo();
		f.getHelper();
	}
}

class Foo {
	private Foo helper = null;

	public Foo getHelper() {
		if (helper == null) // 第一次检查(位置1)
		{
			// 这里会有多于一个的线程同时到达 (位置2)
			synchronized (this) {
				// 这里在每个时刻只能有一个线程 (位置3)
				if (helper == null) // 第二次检查 (位置4)
				{
					helper = new Foo();
				}
			}
		}
		return helper;
	}
	// other functions and members...
}
