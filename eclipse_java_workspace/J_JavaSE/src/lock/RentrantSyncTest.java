package lock;

import java.util.concurrent.locks.ReentrantLock;

public class RentrantSyncTest {
	public static	ReentrantLock reentrant=new ReentrantLock();
	public static synchronized void testRenentrant(int i)  //synchronized这个锁在释放前，这个线程还可继续得到锁
	{
		System.out.println("在锁中 i="+i);
		 if(i-- > 0)
			 testRenentrant(i);
		 System.out.println("解锁了 i="+i);
	}
	public static void main(String[] args) {
	  
		testRenentrant(3);
	}

}
