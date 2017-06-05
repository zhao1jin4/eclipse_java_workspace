package thread.advance;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest
{
	// Condition javadoc example , ArrayBlockingQueue 的功能

	final ReentrantLock lock = new ReentrantLock(); //同一个ReentrantLock ,多线程时只可一个线程进入lock区,同一线程可lock多次
	final Condition notFull = lock.newCondition(); 
	final Condition notEmpty = lock.newCondition(); 
	
	final Object[] items = new Object[100];
	int putptr, takeptr, count;

	public void put(Object x) throws InterruptedException
	{
		//--------
		lock.lock();
		try 
		{
			while (count == items.length)
				notFull.await();
			items[putptr] = x;
			if (++putptr == items.length) 
				putptr = 0;
			++count;
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

  public Object take() throws InterruptedException
  {
		try {
		    lock.lockInterruptibly();//如阻塞在这里进不了, 可以在另个线程中调用   这个线程的对象的 .interupt()，来中断这个线程,会抛出 InterruptedException
		    System.out.println("in lockInterruptibly");
		} catch (InterruptedException e) {
			  System.out.println("-----lockInterruptibly ed ");
		} finally {
			 if(! Thread.interrupted() )//没用？？？
				lock.unlock();//如果被中断的调用unlock有错误
		}
		
		if (lock.tryLock(2,TimeUnit.SECONDS)) {//如果已经被lock，则立即返回false不会等待 ，对多线程来说的
	          try {
	             //操作
	        	  
	        	  lock.lock();
		        	  System.out.println(lock.getHoldCount() );//2
					  System.out.println(lock.isHeldByCurrentThread() );//true
		        	  System.out.println("in tryLock");
	        	  lock.unlock();
	          } finally {
	              lock.unlock();
	          }
	      }
		  
	//--------
	   lock.lock();
	   try 
	   {
		    while (count == 0)
		    	notEmpty.await();
		    Object x = items[takeptr];
		    if (++takeptr == items.length) 
		    	takeptr = 0;
		    --count;
		    notFull.signal();
		    return x;
	   } finally 
	   {
	    lock.unlock();
	   }
  }
//  ReentrantLock reentrantlock = new ReentrantLock(true);//默认是false 不公平
//  public void testOneThreadEnterManyTimes()
//  {
//	  lock.lock();
//	  System.out.println(reentrantlock.getHoldCount() );
//	  System.out.println(reentrantlock.isHeldByCurrentThread() );
//	  
//	  lock.lock();
//	  System.out.println(reentrantlock.getHoldCount() );
//	  System.out.println(reentrantlock.isHeldByCurrentThread() );
//	  
//  }
  public static void main(String[] args) 
  {
	 final ConditionTest test=new ConditionTest();
//	 test.testOneThreadEnterManyTimes();	  
	  
	  
	new Thread("put-1")
	{
		@Override
		public void run() {
			try {
				test.put("put_11");
				test.put("put_22");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}.start();
	
	new Thread("put-2")
	{
		@Override
		public void run() {
			try {
				test.put("put_9999");
				test.put("put_8888");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}.start();
	
	
	
	
	final Thread take1=new Thread("take-1")
	{
		@Override
		public void run() {
			Object res=null;
			try {
				res = test.take();
				System.out.println(res);
				
				res = test.take();
				System.out.println(res);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		}
	};
	take1.start();
	
	
	Thread take2=new Thread("take-2")
	{
		@Override
		public void run() {
			Object res=null;
			try {
				take1.interrupt();
				
				res = test.take();
				System.out.println(res);
				
				res = test.take();
				System.out.println(res);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		}
	};
	take2.start();
	
	
  }
}

 