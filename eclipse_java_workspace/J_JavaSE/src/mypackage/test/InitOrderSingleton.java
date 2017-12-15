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
		System.out.println("num2="+instance.num2);//显示为0的原因是,static按执行顺序,就变为先构造了,所以在static num2前,再给手工指定的值(属性从上到下执行),没有手工给值的不执行
		
		ParentInit x;// 这样不会调用 static 块
		
		Init init=new Init();
		System.out.println("init.a="+init.a);
		
		
//		System.out.println("FinalTest.n="+FinalTest.n);
//		System.out.println("FinalTest.m="+FinalTest.m);
	}
	
}

class ParentInit
{
	public static final int n=6/3;//  final 类型的  6/3  编译时就可以给出的值常量
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
	public Init() //类的初始化顺序   static 属性或块按书写的顺序 (有父类先父类,再子类)  -> 1. {}块  , 初始化非static有值字段 按书写的顺序来 ,2.构造方法 (有父类先父类,再子类)
	{
		a=2;
	}
	int a=1;
} 

