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
	abstract public void test()throws  IOException ;//��д�Խӿڵ��쳣��Χֻ��С,����abstract���߽ӿڷ���,��δ��throws ��дҲ����throws����Runnable
	 
}

class    C extends P 
{
	private void method(int a)throws Exception
	{
		 System.out.println("c a") ;
	}
	
	public synchronized  void method() throws Exception//������ǽӿڿ���
	{
		 System.out.println("P") ;
		 
	}
	public void test()throws  IOException //��д�Խӿڵ��쳣��Χֻ��С
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
	final Lock pauseLock = new ReentrantLock(); //���з�������һ��Lock��ͬʱֻ��ִ��һ������ ,ͬÿ�������ϼ�synchronized
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

//�̵߳ĶԷ�����ִ��˳��
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
