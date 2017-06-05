package jni;

import java.util.Date;

public class TestJavaNative 
{
	public static String USERNAME="lisi李四";//static的
	public int property=10;
	public Parent person=new Child();
	public int[] arrays={5,2,4,8,1,3,7,6};
	public static String staticFunc(int i,double d,char c)//static的
	{
		System.out.println("Java side  funct:");
		return "OK";
	}
	public  boolean function (int foo,Date date,int[] arr,char c)
	{
		System.out.println("Java side Child function: foo="+foo+",date="+date+",arr="+arr+",c="+c);
		return true;
	}
	public native void sayHello();//Java 中声明native方法,表示在C/C++中实现
	public static void main(String[] args) 
	{
		//编译后,使用javah命令,生成 .h文件
		//  J_JavaSE\bin>javah jni.TestNative 生成 jni_TestNative.h
		//System.loadLibrary("TestCPPNative");//要把生成的TestCPPNative.dll放入PATH环境变量中,可能要重启eclipse
		Runtime.getRuntime().loadLibrary("TestCPPNative");
		TestJavaNative test=new TestJavaNative();
		test.sayHello();//调用C
		System.out.println("Java side:property="+test.property);
		System.out.println("Java side:USERNAME="+USERNAME);//OK
		System.out.print("Java side:the arrays Ordered in C side =");
		for (int i : test.arrays)
		{
			System.out.print(i+",");
		}
		
		
	}
}
