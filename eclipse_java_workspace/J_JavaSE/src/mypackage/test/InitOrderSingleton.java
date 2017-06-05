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
		System.out.println("num2="+instance.num2);//��ʾΪ0��ԭ����ִ��ִ��˳��,��Ϊ���Է����ÿռ��Ĭ��ֵ,�ٸ��ֹ�ָ����ֵ(���Դ��ϵ���ִ��),û���ֹ���ֵ�Ĳ�ִ��
		
		Init init=new Init();
		System.out.println("init.a="+init.a);
		
		FinalTest x;// ����������� static ��
//		System.out.println("FinalTest.n="+FinalTest.n);
//		System.out.println("FinalTest.m="+FinalTest.m);
	}
	
}

class FinalTest
{
	public static final int n=6/3;//   6/3  ����ʱ�Ϳ��Ը�����ֵ����
	public static final int m=new Random().nextInt();
	static{
		System.out.println("in FinalTest static block");  
		//��n=6/3 ��final���ﲻ�ᱻִ��,������Random,Ҫ����ʱ��֪����ִ��,������ʼ��,��Ҫִ��,��ֻstaticҲ��ִ��,��Ϊ���ܻᱻ�޸�
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
	public Init() //��ʼ��˳�� static���Կ�->�ȳ�ʼ����static��ֵ�ֶ�->���췽�� 
	{
		a=2;
	}
}
// ��ʼ��һ����ʱ,������ʵ�ֽӿ�,�ӿڲ��ᱻ��ʼ��,  ���ʼ���ӿ�ʱ�����ʼ�����ӿ�,����ʹ�ýӿھ�̬�����ų�ʼ��

 

