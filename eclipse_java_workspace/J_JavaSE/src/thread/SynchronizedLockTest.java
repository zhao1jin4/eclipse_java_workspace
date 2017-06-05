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

//һ���̱߳����Ƚ���  one ���� �绹û�з���,��ڶ����߳�Ҳ���ܽ���  two ,�������߳� Ҳ���ܽ���three ����  (�൱������������һ���߳���ִ��)
//����ʹ��Future ����Ҫ���̴߳�Future����


public class SynchronizedLockTest
{
    public ExecutorService exec =Executors.newCachedThreadPool() ;
    
    //Ĭ����false ����ƽ,��Ϊtrue ѡ��ȴ�ʱ������߳̽���
   	//ǰһ���߳̽���lock()��û���˳�unlock(),��һ���̲߳����Խ���lock(),����ǰһ���߳̽���.newCondition().awaitʱ,��һ���̲߳ſɽ���
  	private static  Lock staticLock = new ReentrantLock(false);  
    private static Condition staticCondition = staticLock.newCondition();  

	//----ReadWriteLock����OK
//	����߳̿�ͬʱ�õ�����Lock����ֻ��һ���߳��ܵõ�д��Lock,����ȶ������
//	����д��Lock���������κ��̶߳����ܵõ�Lock
    ReadWriteLock rwlock = new ReentrantReadWriteLock();
	Lock rlock= rwlock.readLock();
	Lock wlock= rwlock.writeLock();
    int tickNum=20;
    
    //---------ֻ��debug����
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
	
	//---------ֻ��debug����
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
		future.get();//���߳�ִ�����
		
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

 