package mypackage.test;

import java.util.Random;

public class InitOrderSingleton {
	
	private static InitOrderSingleton instance=new InitOrderSingleton();
	private static  int num1;
	private static  int num2=0;
	
	
	
	
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
		System.out.println("num2="+instance.num2);//显示为0的原因是执行执行顺序,先为属性分配置空间给默认值,再给手工指定的值(属性从上到下执行),没有手工给值的不执行
		
		Init init=new Init();
		System.out.println("init.a="+init.a);
		
		FinalTest x;// 这样不会调用 static 块
//		System.out.println("FinalTest.n="+FinalTest.n);
//		System.out.println("FinalTest.m="+FinalTest.m);
	}
	
}

class FinalTest
{
	public static final int n=6/3;//   6/3  编译时就可以给出的值常量
	public static final int m=new Random().nextInt();
	static{
		System.out.println("in FinalTest static block");  
		//如n=6/3 是final这里不会被执行,但如是Random,要运行时才知道会执行,会对类初始化,就要执行,但只static也会执行,因为可能会被修改
	}
}

class Init //extends FinalTest 
{
	int a=1;
	static int s=2;
	static {
		s=3;
		System.out.println("in Init static block");  
	}
	public Init() //初始化顺序 static属性块->先初始化非static有值字段->构造方法 
	{
		a=2;
	}
}
// 初始化一个类时,如有有实现接口,接口不会被初始化,  如初始化接口时不会初始化父接口,除非使用接口静态变量才初始化

 

