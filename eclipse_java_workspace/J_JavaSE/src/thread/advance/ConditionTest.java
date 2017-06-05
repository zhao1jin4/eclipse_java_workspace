package thread.advance;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest
{
	// Condition javadoc example , ArrayBlockingQueue �Ĺ���

	final ReentrantLock lock = new ReentrantLock(); //ͬһ��ReentrantLock ,���߳�ʱֻ��һ���߳̽���lock��,ͬһ�߳̿�lock���
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
		    lock.lockInterruptibly();//�����������������, ����������߳��е���   ����̵߳Ķ���� .interupt()�����ж�����߳�,���׳� InterruptedException
		    System.out.println("in lockInterruptibly");
		} catch (InterruptedException e) {
			  System.out.println("-----lockInterruptibly ed ");
		} finally {
			 if(! Thread.interrupted() )//û�ã�����
				lock.unlock();//������жϵĵ���unlock�д���
		}
		
		if (lock.tryLock(2,TimeUnit.SECONDS)) {//����Ѿ���lock������������false����ȴ� ���Զ��߳���˵��
	          try {
	             //����
	        	  
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
//  ReentrantLock reentrantlock = new ReentrantLock(true);//Ĭ����false ����ƽ
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

 