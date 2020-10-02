package lock;

import java.util.concurrent.locks.ReentrantLock;

public class RentrantLockTest {
	public  	ReentrantLock reentrant=new ReentrantLock(true);//true公平锁
	public   void testRenentrant(int i)
	{ 
		/*
		try {
			//如阻塞在这里进不了,可以在另个线程中调用   这个线程的对象的 .interupt()，来中断这个线程,会抛出 InterruptedException
			reentrant.lockInterruptibly();//lockInterruptibly()可以被thread.interupt()打断阻截如是lock()不可以被打断
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		*/
		reentrant.lock();//调用一次加1
		reentrant.isLocked();
		reentrant.isHeldByCurrentThread();
		reentrant.getHoldCount();
//		Thread.holdsLock(obj);//当前线程 否是synchronize锁这个对象
//		Thread t;
//		t.isAlive();
//		t.getState();
		
		System.out.println(Thread.currentThread().getName()+"在锁中 i="+i);
		 if(i-- > 0)
			 testRenentrant(i);   //这个锁在释放前，这个线程还可继续得到锁,synchronize也可 
		 reentrant.unlock();//但必须解锁到0其它的线程才能进来
		 System.out.println(Thread.currentThread().getName()+"解锁了 i="+i);
	}
	public static void main(String[] args) {
	  
		RentrantLockTest obj=new RentrantLockTest();
		Thread t1=	new Thread("线程1") {
			@Override
			public void run() {
				obj.testRenentrant(3);
			}
		};
	
		Thread t2=new Thread("线程2") {
			@Override
			public void run() {
				obj.testRenentrant(3);
			}
		};
		
		t1.start();
		t2.start();
	}

}
