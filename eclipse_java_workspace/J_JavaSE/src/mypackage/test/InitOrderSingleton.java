package mypackage.test;

import java.util.Random;

public class InitOrderSingleton {
	
	private static InitOrderSingleton instance=new InitOrderSingleton();
	private static  int num1;
	private static  int num2=0;
	
	
	int x=3;
	
	private InitOrderSingleton()
	{
		num1++;
		num2++;
	}
	public static  InitOrderSingleton getInstance()
	{
		return instance;
	}
	
	public static void main(String[] args) {
		 
		InitOrderSingleton instance = InitOrderSingleton.getInstance();
		System.out.println("num1="+instance.num1);
		System.out.println("num2="+instance.num2);//��ʾΪ0��ԭ����,static��ִ��˳��,�ͱ�Ϊ�ȹ�����,������static num2ǰ,�ٸ��ֹ�ָ����ֵ(���Դ��ϵ���ִ��),û���ֹ���ֵ�Ĳ�ִ��
		
		ParentInit x;// ����������� static ��
		
		Init init=new Init();
		System.out.println("init.a="+init.a);
		
		
//		System.out.println("FinalTest.n="+FinalTest.n);
//		System.out.println("FinalTest.m="+FinalTest.m);
	}
	
}

class ParentInit
{
	public static final int n=6/3;//  final ���͵�  6/3  ����ʱ�Ϳ��Ը�����ֵ����
	public static final int m=new Random().nextInt();
	static{
		System.out.println("in ParentInit static block");   
	}
	
	public ParentInit()  
	{
		System.out.println("in Init ParentInit");  
	}
	int a=3;
	{
		System.out.println("in  ParentInit block");  
	}
	
}

class Init  extends ParentInit 
{
	
	static int s=2;
	static {
		s=3;
		System.out.println("in Init static block");  
	}
	{
		System.out.println("in  Init block");  
	}
	public Init() //��ĳ�ʼ��˳��   static ���Ի�鰴��д��˳�� (�и����ȸ���,������)  -> 1. {}��  , ��ʼ����static��ֵ�ֶ� ����д��˳���� ,2.���췽�� (�и����ȸ���,������)
	{
		a=2;
	}
	int a=1;
} 

