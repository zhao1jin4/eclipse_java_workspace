package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//一个线程必须先进入  one 方法 如还没有返回,则第二个线程也不能进入  two ,第三个线程 也不能进入three 方法  (相当于三个方法在一个线程中执行)
//可以使用Future 可能要主线程传Future对象


public class SynchronizedLockTest
{
    public ExecutorService exec =Executors.newCachedThreadPool() ;
    
    //默认是false 不公平,如为true 选择等待时间最长的线程进入
   	//前一个线程进入lock()还没有退出unlock(),后一个线程不可以进入lock(),除非前一个线程进入.newCondition().await时,后一个线程才可进入
  	private static  Lock staticLock = new ReentrantLock(false);  
    private static Condition staticCondition = staticLock.newCondition();  

	//----ReadWriteLock测试OK
//	多个线程可同时得到读的Lock，但只有一个线程能得到写的Lock,必须等读锁完成
//	而且写的Lock被锁定后，任何线程都不能得到Lock
    ReadWriteLock rwlock = new ReentrantReadWriteLock();
	Lock rlock= rwlock.readLock();
	Lock wlock= rwlock.writeLock();
    int tickNum=20;
    
    //---------只做debug测试
	public    void saleTicket()
	{
		wlock.lock();
			tickNum--;
		wlock.unlock();
	}
	public    void readLockTicket()
	{
		rlock.lock();
			System.out.println(Thread.currentThread().getName()+"_tickNum"+tickNum);
		rlock.unlock();
	}
	
	//---------只做debug测试
	public   static void oneStatic()
	{
		staticLock.lock();
		System.out.println(Thread.currentThread().getName()+"_in static one");
		staticCondition.signal();
		staticLock.unlock();
	}
	public   static void twoStatic() throws Exception
	{
		staticLock.lock();
		staticCondition.await();
		System.out.println(Thread.currentThread().getName()+"_in static two");
		staticLock.unlock();
	}
	 
  public static void main(String[] args) throws Exception 
  {
	  
	 final SynchronizedLockTest test=new SynchronizedLockTest();
	 
	 new Thread("my-1")
		{
			@Override
			public void run() {
				try {
//					test.readLockTicket();
//					test.saleTicket();
					
					//----
					//SynchronizedLockTest.oneStatic();
	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		new Thread("my-2")
		{
			@Override
			public void run() {
				
				try {
//					test.saleTicket();
//					test.readLockTicket();
					//----
					//SynchronizedLockTest.twoStatic();
					 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		
		Future<Integer> future =test.exec.submit(new Callable<Integer>(){
			@Override
			public Integer call() throws Exception {
				System.out.println(Thread.currentThread().getName()+"_in one");
				return 1;
			}
		});
		future.get();//等线程执行完成
		
		future =test.exec.submit(new Callable<Integer>(){
			@Override
			public Integer call() throws Exception {
				System.out.println(Thread.currentThread().getName()+"_in two");
				return 1;
			}
		});
		future.get();
		test.exec.shutdown();
		 
  }
}

 