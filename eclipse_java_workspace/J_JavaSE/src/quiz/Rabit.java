package quiz;

import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Rabit {

	
	public static long rabit(long n)
	{	
		//java.lang.StackOverflowError
		if (n<=2)
			return 1;
		else 
		{
			int abc=1836311903;
					
			/*
			int i=1234567890l;
			    //1836311903 //45
			int  a=1234567890 + 1234567890;//负数溢出
			long  b=1234567890l + 1234567890l;//加l
			*/
			
			long a=rabit(n-1);
			long b=rabit(n-2);
			long s=a+b;
			return s;
				
		
		}
	}
	
	
	
	//潭浩强  Fibonacci 数学问题 兔子 三个月生
	//如果一对兔子每月能生一对小兔（一雄一雌），而每对小兔在它出生后的第三个月里，又能开始生一对小兔
	public static long rabit2(long n)// n=11;//月数
	{
		long  f1=1,f2=1;
		for(int i=1;i<=n/2;i++)  // 除2是因加了两次
		{
			System.out.printf("%12d  %12d\n",f1,f2);
			f1=f1+f2;
			f2=f2+f1;
		}
		return f1;
		
		
	}
	

	public static void main(String[] args) 
	{
		
		//java.lang.StackOverflowError
		//long count=rabit(41);//41=165580141 ,42=267914296, 如传42的话非常的慢,如更大就更不用说了
		//long count=rabit2(5);//不用递归速度更快,  
		//System.out.println("==="+count);
		
		
		
		//第一个人10，第二个人比第一个人大5岁，求87个人多大
		int sumAge=10;
		for(int i=1;i<=87;i++)
		{
			sumAge+=5;
		}
		System.out.println(sumAge);
		
		int age=testRecursveCommon(10,1,87);
		//int age=testRecursveNew(10,1,87);
		System.out.println(age);
		
	}
	
	
	public static int testRecursveCommon(int age,int currentTimes,int totalTimes) 
	{
		if(currentTimes++  >totalTimes)
			return age;
		age=testRecursveCommon(age,currentTimes, totalTimes);
		age=age+5;
		return age;
	}
	
	
	public static void testRecursveNew(int age,int currentTimes,int totalTimes) 
	{
		
		ForkJoinPool pool = new ForkJoinPool(); //Fork/Join 模式 ,默认是runtime.availableProcessors();CPU多少核的
		pool.invoke(new   RecursiveAction() //如使用RecursiveTask的compute方法可带返回值
		{
			protected void compute()
			{
					
			//if(数量少)
			//		直接调用
			//else  数量多
			//		递归拆分多个,一般是二分法
			//		invokeAll(left,right); //invokeAll是ForkJoinTask的方法
			//		compute方法没有返回值,这里可以保存递归值
			
//				.fork();//子任务的异步执行,每次调用增加一个线程,直到 runtime.availableProcessors()个线程为止,如果超过getSurplusQueuedTaskCount()返回值加1
//				.join()//阻塞等待结果完成。
			}
		});
	}
	
	

}
