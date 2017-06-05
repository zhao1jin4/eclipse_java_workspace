package jni;

import java.util.Date;

public class TestJavaNative 
{
	public static String USERNAME="lisi����";//static��
	public int property=10;
	public Parent person=new Child();
	public int[] arrays={5,2,4,8,1,3,7,6};
	public static String staticFunc(int i,double d,char c)//static��
	{
		System.out.println("Java side  funct:");
		return "OK";
	}
	public  boolean function (int foo,Date date,int[] arr,char c)
	{
		System.out.println("Java side Child function: foo="+foo+",date="+date+",arr="+arr+",c="+c);
		return true;
	}
	public native void sayHello();//Java ������native����,��ʾ��C/C++��ʵ��
	public static void main(String[] args) 
	{
		//�����,ʹ��javah����,���� .h�ļ�
		//  J_JavaSE\bin>javah jni.TestNative ���� jni_TestNative.h
		//System.loadLibrary("TestCPPNative");//Ҫ�����ɵ�TestCPPNative.dll����PATH����������,����Ҫ����eclipse
		Runtime.getRuntime().loadLibrary("TestCPPNative");
		TestJavaNative test=new TestJavaNative();
		test.sayHello();//����C
		System.out.println("Java side:property="+test.property);
		System.out.println("Java side:USERNAME="+USERNAME);//OK
		System.out.print("Java side:the arrays Ordered in C side =");
		for (int i : test.arrays)
		{
			System.out.print(i+",");
		}
		
		
	}
}
