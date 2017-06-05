package quiz;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

interface I
{
	public void test() throws IOException;
}
  abstract class P implements I
{

	private void method() throws  IOException
	{
		 System.out.println("P ") ;
	}
	private void method(int a )
	{
		 System.out.println("P a") ;
	}
	abstract public void test()throws  IOException ;//重写对接口的异常范围只能小,如是abstract或者接口方法,如未定throws 重写也不能throws就像Runnable
	 
}

class    C extends P 
{
	private void method(int a)throws Exception
	{
		 System.out.println("c a") ;
	}
	
	public synchronized  void method() throws Exception//如果不是接口可以
	{
		 System.out.println("P") ;
		 
	}
	public void test()throws  IOException //重写对接口的异常范围只能小
	{
		 System.out.println("test") ;
	}
	
}



class Busniness
{
	public void methodA()
	{
		System.out.println("methodA");
	}
	
	public void methodB()
	{
		System.out.println("methodB");
	}
	public   void methodC()
	{
		System.out.println("methodC");
	}
}
class LockBussiness extends Busniness
{
	final Lock pauseLock = new ReentrantLock(); //所有方法都用一个Lock，同时只能执行一个方法 ,同每个方法上加synchronized
	final Condition bCondition = pauseLock.newCondition();
	final Condition cCondition = pauseLock.newCondition();
	boolean bWait=true;
	boolean cWait=true;
	
	public   void methodA()  
	{
		pauseLock.lock();
		try {  
			super.methodA();
			bWait=false;
			bCondition.signal();
        } finally {  
        	pauseLock.unlock();
        }
	}
	
	public   void methodB()  
	{
		pauseLock.lock();
		try {
			while(bWait)//A "spurious wakeup" occurs. 
				bCondition.await();
			super.methodB();
			cWait=false;
			cCondition.signal();
        } catch (InterruptedException e)
		{
			e.printStackTrace();
		} finally {  
        	pauseLock.unlock();
        }
		
	}
	public    void methodC()  
	{
		pauseLock.lock();
		try { 
			while(cWait)
				cCondition.await();
			super.methodC();
        } catch (InterruptedException e)
		{
			e.printStackTrace();
		} finally {  
        	pauseLock.unlock();
        }
	}
	
}

//线程的对方法的执行顺序
public class ThreadOrder
{
	public static void main(String[] args)
	{
		C c=new C();
		try
		{
			c.method ();
		} catch ( Exception e)
		{
			e.printStackTrace();
		}
		//-------------------OK
		final  LockBussiness bus=new LockBussiness();
		Thread aThread=	new Thread()
		{
			public void run() 
			{
				bus.methodA();
				bus.methodC();
			}
		};
		Thread bThread=new Thread( )
		{
			public void run( )
			{
				bus.methodB();
			}
		};
		aThread.start();
		bThread.start();
 	}
 
}
